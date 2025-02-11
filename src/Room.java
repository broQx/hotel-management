public class Room {
    private int roomId;
    private String type; // тип комнаты (например, одноместный, двухместный)
    private double price; // цена за ночь
    private int stock; // количество доступных номеров

    // Конструкторы
    public Room() {}

    public Room(String type, double price, int stock) {
        this.type = type;
        this.price = price;
        this.stock = stock;
    }

    // Геттеры и сеттеры
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Метод для отображения информации о номере
    public void displayInfo() {
        System.out.printf("Room ID: %d, Type: %s, Price: %.2f, Available: %d%n", roomId, type, price, stock);
    }
}
