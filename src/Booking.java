import java.util.Date;

public class Booking {
    private int bookingId;
    private int userId;
    private int roomId;
    private String bookingDate; // Дата бронирования

    // Конструкторы
    public Booking(int userId, int roomId, Date sqlDate) {}

    public Booking(int userId, int roomId, String bookingDate) {
        this.userId = userId;
        this.roomId = roomId;
        this.bookingDate = bookingDate;
    }

    // Геттеры и сеттеры
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    // Метод для сохранения бронирования в базе данных
    public void saveBooking() {
        // Здесь будет логика для сохранения бронирования в базе данных
    }
}
