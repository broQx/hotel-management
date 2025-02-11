import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static DatabaseHandler instance;
    private Connection connection;

    // Обновите URL, имя пользователя и пароль для вашего проекта
    private static final String URL = "jdbc:postgresql://localhost:5432/hotel_management";  // Название базы данных
    private static final String USER = "postgres";  // Имя пользователя
    private static final String PASSWORD = "2006";  // Ваш пароль

    static {
        try {
            Class.forName("org.postgresql.Driver"); // Убедитесь, что драйвер PostgreSQL доступен
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver Not Found!", e);
        }
    }

    // Конструктор
    public DatabaseHandler() {
        connect();
    }

    // Метод для подключения к базе данных
    private void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("ERROR: Failed to connect to the database!");
            e.printStackTrace();
        }
    }

    // Синглтон для получения экземпляра DatabaseHandler
    public static synchronized DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    // Метод для получения соединения
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();  // Если соединение закрыто, пробуем подключиться заново
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to reconnect to the database!", e);
        }
        return connection;
    }
}