import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelService {
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    // Получение доступных номеров
    public List<Room> getAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE stock > 0";  // Номера, где есть запас

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomId(resultSet.getInt("room_id"));
                room.setType(resultSet.getString("type"));
                room.setPrice(resultSet.getDouble("price"));
                room.setStock(resultSet.getInt("stock"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching available rooms: " + e.getMessage());
        }
        return rooms;
    }

    // Добавление нового номера
    public void addRoom(Room room) {
        String query = "INSERT INTO rooms (type, price, stock) VALUES (?, ?, ?)";

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, room.getType());
            statement.setDouble(2, room.getPrice());
            statement.setInt(3, room.getStock());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Room added successfully!");
            } else {
                System.out.println("Failed to add room.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
        }
    }

    // Удаление номера
    public void deleteRoom(int roomId) {
        String query = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Room deleted successfully!");
            } else {
                System.out.println("Room not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting room: " + e.getMessage());
        }
    }

    // Обновление информации о номере
    public void updateRoom(Room room) {
        String query = "UPDATE rooms SET type = ?, price = ?, stock = ? WHERE room_id = ?";

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, room.getType());
            statement.setDouble(2, room.getPrice());
            statement.setInt(3, room.getStock());
            statement.setInt(4, room.getRoomId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Room details updated successfully!");
            } else {
                System.out.println("Room not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating room: " + e.getMessage());
        }
    }

    // Получение всех номеров
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomId(resultSet.getInt("room_id"));
                room.setType(resultSet.getString("type"));
                room.setPrice(resultSet.getDouble("price"));
                room.setStock(resultSet.getInt("stock"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all rooms: " + e.getMessage());
        }
        return rooms;
    }

    // Получение общего числа номеров
    public int getTotalRooms() {
        String query = "SELECT COUNT(*) FROM rooms";

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total rooms: " + e.getMessage());
        }
        return 0;
    }

    // Получение общего числа бронирований
    public int getTotalBookings() {
        String query = "SELECT COUNT(*) FROM bookings";

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total bookings: " + e.getMessage());
        }
        return 0;
    }

    // Получение количества номеров с низким запасом
    public int getLowStockRooms() {
        String query = "SELECT COUNT(*) FROM rooms WHERE stock < 5";

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching low stock rooms: " + e.getMessage());
        }
        return 0;
    }

    // Добавление нового бронирования
    public void addBooking(Booking booking) {
        String query = "INSERT INTO bookings (user_id, room_id, booking_date) VALUES (?, ?, ?)";

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, booking.getUserId());
            statement.setInt(2, booking.getRoomId());
            statement.setString(3, booking.getBookingDate());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Booking added successfully!");
            } else {
                System.out.println("Failed to add booking.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding booking: " + e.getMessage());
        }
    }

    public Room getRoomById(int roomId) {
        String query = "SELECT * FROM rooms WHERE room_id = ?";  // Запрос для получения информации о номере по ID

        try (Connection connection = databaseHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, roomId);  // Устанавливаем параметр в запрос (roomId)

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Если номер найден, создаем объект Room и заполняем его данными из базы
                    Room room = new Room();
                    room.setRoomId(resultSet.getInt("room_id"));
                    room.setType(resultSet.getString("type"));
                    room.setPrice(resultSet.getDouble("price"));
                    room.setStock(resultSet.getInt("stock"));
                    return room;  // Возвращаем объект Room
                } else {
                    System.out.println("Room not found with the given ID.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching room by ID: " + e.getMessage());
        }
        return null;  // Возвращаем null, если номер не найден
    }
}
