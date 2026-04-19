import java.io.*;

class Booking implements Serializable {
    String customerName;
    int roomNumber;
    int nights;
    double totalAmount;

    public Booking(String customerName, int roomNumber, int nights, double totalAmount) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.totalAmount = totalAmount;
    }
}