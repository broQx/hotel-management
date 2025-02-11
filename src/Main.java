import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static HotelService hotelService = new HotelService();
    private static DatabaseHandler databaseHandler = new DatabaseHandler();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nHotel Management System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        User user = getUserFromDatabase(userId);
        if (user == null) {
            System.out.println("User not found. Please register first.");
            return;
        }

        if ("Admin".equalsIgnoreCase(user.getRole())) {
            System.out.print("Enter admin password: ");
            String password = scanner.nextLine();
            if ("admin123".equals(password)) {
                adminMenu();
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            customerMenu(user);
        }
    }

    private static void register() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        User existingUser = getUserFromDatabase(userId);
        if (existingUser != null) {
            System.out.println("User ID already exists. Registration failed.");
            return;
        }

        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter user email: ");
        String email = scanner.nextLine();

        System.out.print("Enter user role (Admin/Customer): ");
        String role = scanner.nextLine();

        String query = "INSERT INTO users (user_id, name, email, role) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View all rooms");
            System.out.println("2. Add a new room");
            System.out.println("3. Delete a room");
            System.out.println("4. Update room details");
            System.out.println("5. View statistics");
            System.out.println("6. Search room by ID");
            System.out.println("7. Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewAllRooms();
                    break;
                case 2:
                    addNewRoom();
                    break;
                case 3:
                    deleteRoom();
                    break;
                case 4:
                    updateRoomDetails();
                    break;
                case 5:
                    viewStatistics();
                    break;
                case 6:
                    searchRoomById();
                    break;
                case 7:
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void customerMenu(User customer) {
        while (true) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. View available rooms");
            System.out.println("2. Make a booking");
            System.out.println("3. Search room by ID");
            System.out.println("4. Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewAvailableRooms();
                    break;
                case 2:
                    makeBooking(customer);
                    break;
                case 3:
                    searchRoomById();
                    break;
                case 4:
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void viewAllRooms() {
        List<Room> rooms = hotelService.getAllRooms();
        for (Room room : rooms) {
            room.displayInfo();
        }
    }

    private static void addNewRoom() {
        System.out.print("Enter room type: ");
        String type = scanner.nextLine();
        System.out.print("Enter room price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter room stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Room newRoom = new Room(type, price, stock);
        hotelService.addRoom(newRoom);
        System.out.println("New room added successfully.");
    }

    private static void deleteRoom() {
        System.out.print("Enter room ID to delete: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        hotelService.deleteRoom(roomId);
        System.out.println("Room deleted successfully.");
    }

    private static void updateRoomDetails() {
        System.out.print("Enter room ID to update: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Room room = hotelService.getRoomById(roomId);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.print("Enter new room price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new room stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // consume newline

        room.setPrice(price);
        room.setStock(stock);
        hotelService.updateRoom(room);
        System.out.println("Room details updated successfully.");
    }

    private static void viewStatistics() {
        System.out.println("Total rooms in the hotel: " + hotelService.getTotalRooms());
        System.out.println("Total bookings: " + hotelService.getTotalBookings());
        System.out.println("Rooms with low stock: " + hotelService.getLowStockRooms());
    }

    private static void makeBooking(User customer) {
        System.out.println("Available rooms:");
        viewAvailableRooms();

        System.out.print("Enter room ID to book: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter booking date (YYYY-MM-DD): ");
        String bookingDateStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(bookingDateStr);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            // Выполнение запроса для добавления бронирования
            String query = "INSERT INTO bookings (user_id, room_id, booking_date) VALUES (?, ?, ?)";
            try (Connection connection = databaseHandler.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, customer.getUserId());
                statement.setInt(2, roomId);
                statement.setDate(3, sqlDate);  // Используем setDate для передачи java.sql.Date

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Booking made successfully.");
                } else {
                    System.out.println("Error adding booking.");
                }
            } catch (SQLException e) {
                System.err.println("Error adding booking: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private static void searchRoomById() {
        System.out.print("Enter room ID to search: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "SELECT * FROM rooms WHERE room_id = ?";
        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                System.out.printf("Room ID: %d, Type: %s, Price: %.2f, Available: %d%n",
                        roomId, type, price, stock);
            } else {
                System.out.println("Room not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching room details: " + e.getMessage());
        }
    }



    private static void viewAvailableRooms() {
        List<Room> rooms = hotelService.getAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("No available rooms.");
        } else {
            for (Room room : rooms) {
                room.displayInfo();
            }
        }
    }

    private static User getUserFromDatabase(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(userId);
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }
}

