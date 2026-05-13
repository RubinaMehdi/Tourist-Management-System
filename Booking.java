import java.util.ArrayList;
import java.io.*;

class Tourist extends Person {
    public Tourist(String id, String name, String email, String pass) {
        super(id, name, email, pass);
    }
}

class TourPackage {
    String packageId, title;
    double price;
    public TourPackage(String packageId, String title, double price) {
        this.packageId = packageId;
        this.title = title;
        this.price = price;
    }
}

class Hotel {
    String hotelId, hotelName;
    int stars;
    public Hotel(String hotelId, String hotelName, int stars) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.stars = stars;
    }
}

class TourGuide extends Person {
    public TourGuide(String id, String name, String email, String pass) {
        super(id, name, email, pass);
    }
}

class Dining {
    String diningId, restaurantName, cuisine;
    public Dining(String diningId, String restaurantName, String cuisine) {
        this.diningId = diningId;
        this.restaurantName = restaurantName;
        this.cuisine = cuisine;
    }
}

public class Booking {

    private String bookingId;
    private Tourist tourist;
    private TourPackage packages;
    private Hotel hotel;
    private TourGuide guide;
    private Dining dining;
    private boolean isConfirmed = false;

    static ArrayList<Booking> bookingList = new ArrayList<Booking>();

    public Booking(String bookingId, Tourist tourist, TourPackage packages,
                   Hotel hotel, TourGuide guide, Dining dining) {
        this.bookingId = bookingId;
        this.tourist = tourist;
        this.packages = packages;
        this.hotel = hotel;
        this.guide = guide;
        this.dining = dining;
    }

    public void selectPackage() {
        System.out.println("Package selected: " + packages.title);
    }

    public void confirmBooking() {
        isConfirmed = true;
        System.out.println("Booking " + bookingId + " confirmed.");
    }

    public void cancelBooking() {
        isConfirmed = false;
        System.out.println("Booking " + bookingId + " cancelled.");
    }

    public void viewBooking() {
        System.out.println("\n--- Booking: " + bookingId + " ---");
        System.out.println("Tourist  : " + tourist.getProfile());
        System.out.println("Package  : " + packages.title + " ($" + packages.price + ")");
        System.out.println("Hotel    : " + hotel.hotelName);
        System.out.println("Guide    : " + guide.getProfile());
        System.out.println("Dining   : " + dining.restaurantName);
        System.out.println("Status   : " + (isConfirmed ? "Confirmed" : "Pending"));
        System.out.println("-----------------------------");
    }

    public static void addBooking(Booking b) {
        bookingList.add(b);
    }

    public static void removeBooking(String id) {
        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).bookingId.equals(id)) {
                bookingList.remove(i);
                System.out.println("Booking removed: " + id);
                return;
            }
        }
        System.out.println("Booking not found: " + id);
    }

    public static void displayAll() {
        System.out.println("\n--- All Bookings ---");
        for (int i = 0; i < bookingList.size(); i++) {
            Booking b = bookingList.get(i);
            System.out.println("ID: " + b.bookingId + " | Status: " + (b.isConfirmed ? "Confirmed" : "Pending"));
        }
        System.out.println("--------------------");
    }

    public static void saveToFile(String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < bookingList.size(); i++) {
                Booking b = bookingList.get(i);
                bw.write(b.bookingId + ","
                        + b.tourist.personId + "," + b.tourist.name + "," + b.tourist.email + "," + b.tourist.password + ","
                        + b.packages.packageId + "," + b.packages.title + "," + b.packages.price + ","
                        + b.hotel.hotelId + "," + b.hotel.hotelName + "," + b.hotel.stars + ","
                        + b.guide.personId + "," + b.guide.name + "," + b.guide.email + "," + b.guide.password + ","
                        + b.dining.diningId + "," + b.dining.restaurantName + "," + b.dining.cuisine + ","
                        + b.isConfirmed);
                bw.newLine();
            }
            bw.close();
            System.out.println("Bookings saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public static void loadFromFile(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            bookingList.clear();
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                Tourist t   = new Tourist(p[1], p[2], p[3], p[4]);
                TourPackage pkg = new TourPackage(p[5], p[6], Double.parseDouble(p[7]));
                Hotel h   = new Hotel(p[8], p[9], Integer.parseInt(p[10]));
                TourGuide g   = new TourGuide(p[11], p[12], p[13], p[14]);
                Dining d   = new Dining(p[15], p[16], p[17]);
                Booking b   = new Booking(p[0], t, pkg, h, g, d);
                b.isConfirmed   = Boolean.parseBoolean(p[18]);
                bookingList.add(b);
            }
            br.close();
            System.out.println("Bookings loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        Tourist t1  = new Tourist("T001", "Ali Hassan", "ali@email.com",  "t1pass");
        Tourist t2  = new Tourist("T002", "Zara Malik", "zara@email.com", "t2pass");
        TourPackage pk1 = new TourPackage("PKG01", "Northern Peaks",  1200);
        TourPackage pk2 = new TourPackage("PKG02", "Lahore Heritage",  800);
        Hotel h1  = new Hotel("H01", "Serena Hotel",      5);
        Hotel h2  = new Hotel("H02", "Pearl Continental", 5);
        TourGuide g1  = new TourGuide("G01", "Usman Malik", "usman@guide.com", "g1pass");
        TourGuide g2  = new TourGuide("G02", "Nadia Iqbal", "nadia@guide.com", "g2pass");
        Dining d1  = new Dining("D01", "Monal Restaurant",  "Pakistani");
        Dining d2  = new Dining("D02", "Andaaz Restaurant", "Mughal");

        Booking b1 = new Booking("B001", t1, pk1, h1, g1, d1);
        Booking b2 = new Booking("B002", t2, pk2, h2, g2, d2);

        addBooking(b1);
        addBooking(b2);

        b1.confirmBooking();
        b1.viewBooking();
        b2.viewBooking();
        displayAll();

        saveToFile("bookings.txt");

        bookingList.clear();
        System.out.println("List cleared. Reloading...");
        loadFromFile("bookings.txt");
        displayAll();

        removeBooking("B001");
        displayAll();
        saveToFile("bookings.txt");
    }
}
