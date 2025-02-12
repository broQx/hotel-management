# Hotel Management System - README

This is a simple Java-based hotel management system. It allows users to register, login, and interact with the system as either an administrator or a customer. Administrators can manage rooms and view statistics, while customers can make bookings. The system is connected to a database to handle user and room data.

## Features

### 1. **User Registration and Login**
   - **Registration**: Users can register by providing a unique user ID, name, email, and role (`Admin` or `Customer`).
   - **Login**: Users can log in using their user ID. If the role is `Admin`, they will be prompted to enter the admin password (`admin123`).

### 2. **Admin Menu**
   Once logged in as an admin, users can access the following features:
   - **View all rooms**: View a list of all rooms in the hotel.
   - **Add a new room**: Add a new room by specifying its type, price, and stock.
   - **Delete a room**: Delete a room by specifying its room ID.
   - **Update room details**: Update a room’s price and stock.
   - **View statistics**: View hotel statistics, including the total number of rooms, total bookings, and rooms with low stock.
   - **Search room by ID**: Search for a room by its unique room ID.

### 3. **Customer Menu**
   Once logged in as a customer, users can access the following features:
   - **View available rooms**: View rooms that are available for booking.
   - **Make a booking**: Make a booking by selecting an available room and specifying a booking date.
   - **Search room by ID**: Search for a room by its unique room ID.

### 4. **Room Management**
   - **View all rooms**: View a list of all rooms, their types, prices, and available stock.
   - **Add new rooms**: Add new rooms to the hotel system by specifying the room type, price, and stock.
   - **Delete rooms**: Remove a room from the system by specifying its ID.
   - **Update room details**: Modify the price and stock of an existing room.
   - **View available rooms**: Check which rooms are available for booking.

### 5. **Database Integration**
   The system is connected to a PostgreSQL database for storing and retrieving user and room data. The database operations are handled using JDBC (Java Database Connectivity).

## Requirements
- **Java 8 or higher**
- **PostgreSQL database**
- **JDBC Driver for PostgreSQL**

## Setup

### 1. **Database Setup**
   To get started, create a PostgreSQL database and run the following SQL commands to create the necessary tables for users and rooms.

   ```sql
   -- Create Users Table
   CREATE TABLE users (
       user_id SERIAL PRIMARY KEY,
       name VARCHAR(100),
       email VARCHAR(100),
       role VARCHAR(20)
   );

   -- Create Rooms Table
   CREATE TABLE rooms (
       room_id SERIAL PRIMARY KEY,
       type VARCHAR(50),
       price DECIMAL(10, 2),
       stock INT
   );

   -- Create Bookings Table
   CREATE TABLE bookings (
       booking_id SERIAL PRIMARY KEY,
       user_id INT REFERENCES users(user_id),
       room_id INT REFERENCES rooms(room_id),
       booking_date DATE
   );
   ```

### 2. **Database Configuration**
   The `DatabaseHandler` class is responsible for handling database connections. Make sure to configure the database connection details, such as the database URL, username, and password, in the `DatabaseHandler` class.

   Example of a connection string:
   ```java
   String url = "jdbc:postgresql://localhost:5432/your_database_name";
   String user = "your_username";
   String password = "your_password";
   ```

### 3. **Running the Application**
   - Compile and run the `Main.java` file.
   - The system will prompt you to either log in or register.
   - Depending on the role (Admin/Customer), different menus will be displayed with corresponding options.
   - As an admin, you can manage rooms, view statistics, and search for rooms by ID.
   - As a customer, you can book available rooms and search for rooms by ID.

## Methods and Functionality

### 1. **Main Methods**:
   - `main(String[] args)`: The entry point of the application. Displays the main menu and handles user input.
   - `login()`: Allows users to log in using their user ID and role. Admins are prompted to enter a password.
   - `register()`: Registers a new user in the database.

### 2. **Admin Menu**:
   - `adminMenu()`: Displays the admin menu and allows the admin to perform room-related operations such as adding, deleting, and updating rooms.
   - `viewAllRooms()`: Displays all rooms in the hotel.
   - `addNewRoom()`: Allows the admin to add a new room.
   - `deleteRoom()`: Allows the admin to delete a room by its ID.
   - `updateRoomDetails()`: Allows the admin to update room details such as price and stock.
   - `viewStatistics()`: Displays hotel statistics such as total rooms, bookings, and low stock rooms.
   - `searchRoomById()`: Allows the admin to search for a room by its ID.

### 3. **Customer Menu**:
   - `customerMenu(User customer)`: Displays the customer menu, where the customer can view available rooms and make bookings.
   - `makeBooking(User customer)`: Allows the customer to make a booking for an available room.
   - `viewAvailableRooms()`: Displays rooms that are available for booking.
   - `searchRoomById()`: Allows the customer to search for a room by its ID.

### 4. **Helper Methods**:
   - `getUserFromDatabase(int userId)`: Fetches the user from the database using the provided user ID.

## Example

### Registering a New User:
   - Enter user ID, name, email, and role (Admin or Customer).
   - The system will store the information in the `users` table of the database.

### Admin Menu:
   - The admin can add rooms, view statistics, or search rooms by ID.

### Customer Menu:
   - The customer can view available rooms and make bookings by selecting the room and entering a booking date.

## Conclusion

This project demonstrates a basic hotel management system with user registration, login, and role-based access. The admin has full control over room management, while the customer can make bookings. The system integrates with a PostgreSQL database to store and retrieve user and room data. 

