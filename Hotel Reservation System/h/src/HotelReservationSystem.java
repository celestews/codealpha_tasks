import java.io.*;
import java.util.*;

public class HotelReservationSystem {
    private static List<Room> rooms = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static final String DATA_FILE = "hotel_data.txt";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();
        loadData(); 

        while (true) {
            System.out.println("\n~ HOTEL RESERVATION SYSTEM ~");
            System.out.println("1. Search Available Rooms\n2. Book a Room\n3. Cancel Reservation\n4. View Booking Details\n5. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> showAvailableRooms();
                case 2 -> makeReservation();
                case 3 -> cancelReservation();
                case 4 -> viewBookings();
                case 5 -> { saveData(); System.exit(0); }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void initializeRooms() {
        if (rooms.isEmpty()) {
            rooms.add(new Room(101, "Standard", 50.0));
            rooms.add(new Room(102, "Standard", 50.0));
            rooms.add(new Room(201, "Deluxe", 100.0));
            rooms.add(new Room(301, "Suite", 250.0));
        }
    }

    private static void showAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        rooms.stream().filter(Room::isAvailable).forEach(System.out::println);
    }

    private static void makeReservation() {
        System.out.print("\nEnter Room Number: ");
        int rNum = sc.nextInt();
        Room room = findRoom(rNum);

        if (room != null && room.isAvailable()) {
            System.out.print("Enter Customer Name: ");
            String name = sc.next();
            System.out.print("Number of nights: ");
            int nights = sc.nextInt();

            double total = room.getPrice() * nights;
            System.out.println("Total Price: $" + total);
            
            
            System.out.print("Process Payment? (yes/no): ");
            if (sc.next().equalsIgnoreCase("yes")) {
                room.setAvailable(false);
                bookings.add(new Booking(name, rNum, nights, total));
                System.out.println("Booking Successful!");
            }
        } else {
            System.out.println("Room unavailable or does not exist.");
        }
    }

    private static void cancelReservation() {
        System.out.print("\nEnter Room Number to cancel: ");
        int rNum = sc.nextInt();
        bookings.removeIf(b -> b.roomNumber == rNum);
        Room room = findRoom(rNum);
        if (room != null) room.setAvailable(true);
        System.out.println("Reservation Cancelled.");
    }

    private static void viewBookings() {
        if(bookings.isEmpty()){
            System.out.println("\nThere hasn't been any bookings yet.");
        }
        System.out.println("\n~ Current Bookings ~");
        for (Booking b : bookings) {
            System.out.println("Guest: " + b.customerName + " | Room: " + b.roomNumber + " | Total: $" + b.totalAmount);
        }
    }

    private static Room findRoom(int num) {
        return rooms.stream().filter(r -> r.getRoomNumber() == num).findFirst().orElse(null);
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(bookings);
            System.out.println("Data saved successfully.");
        } 
        catch (IOException e) { System.out.println("Error saving data."); }
    }

    
    private static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            bookings = (List<Booking>) ois.readObject();
            for (Booking b : bookings) {
                Room r = findRoom(b.roomNumber);
                if (r != null) r.setAvailable(false);
            }
        } 
        catch (FileNotFoundException e) {
            bookings = new ArrayList<>(); 
            System.out.println("No existing data found! Starting with a fresh database.");
        } 
        catch (IOException | ClassNotFoundException e) {
            bookings = new ArrayList<>();
            System.err.println("Error loading data: " + e.getMessage());
            System.out.println("Initialization failed! Starting with empty bookings.");
       }

    }
}
