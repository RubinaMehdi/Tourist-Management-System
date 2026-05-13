import java.util.ArrayList;
import java.io.*;

class TourPackage {
    String packageId, title;
    double price;
    public TourPackage(String packageId, String title, double price) {
        this.packageId = packageId;
        this.title     = title;
        this.price     = price;
    }
}

class Hotel {
    String hotelId, hotelName;
    int stars;
    public Hotel(String hotelId, String hotelName, int stars) {
        this.hotelId   = hotelId;
        this.hotelName = hotelName;
        this.stars     = stars;
    }
}

class Dining {
    String diningId, restaurantName, cuisine;
    public Dining(String diningId, String restaurantName, String cuisine) {
        this.diningId       = diningId;
        this.restaurantName = restaurantName;
        this.cuisine        = cuisine;
    }
}

public class Booking {

    private String      bookingId;
    private Person      tourist;      // Person object (no Tourist class)
    private Person      guide;        // Person object (no TourGuide class)
    private TourPackage packages;
    private Hotel       hotel;
    private Dining      dining;
    private boolean     isConfirmed = false;

    static ArrayList<Booking> bookingList = new ArrayList<Booking>();

    public Booking(String bookingId, Person tourist, TourPackage packages,
                   Hotel hotel, Person guide, Dining dining) {
        this.bookingId = bookingId;
        this.tourist   = tourist;
        this.packages  = packages;
        this.hotel     = hotel;
        this.guide     = guide;
        this.dining    = dining;
    }

    // ── Methods ──────────────────────────────
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

    // ── ArrayList Methods ────────────────────
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

    // ── File I/O ─────────────────────────────
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
            System.out.println("Saved to " + filename);
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
                String[] p  = line.split(",");
                Person      t   = new Person(p[1], p[2], p[3], p[4]);
                TourPackage pkg = new TourPackage(p[5], p[6], Double.parseDouble(p[7]));
                Hotel       h   = new Hotel(p[8], p[9], Integer.parseInt(p[10]));
                Person      g   = new Person(p[11], p[12], p[13], p[14]);
                Dining      d   = new Dining(p[15], p[16], p[17]);
                Booking     b   = new Booking(p[0], t, pkg, h, g, d);
                b.isConfirmed   = Boolean.parseBoolean(p[18]);
                bookingList.add(b);
            }
            br.close();
            System.out.println("Loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }

    // ── Main ─────────────────────────────────
    public static void main(String[] args) {

        Person      t1  = new Person("T001", "Ali Hassan", "ali@email.com",  "t1pass");
        Person      t2  = new Person("T002", "Zara Malik", "zara@email.com", "t2pass");
        TourPackage pk1 = new TourPackage("PKG01", "Northern Peaks",  1200);
        TourPackage pk2 = new TourPackage("PKG02", "Lahore Heritage",  800);
        Hotel       h1  = new Hotel("H01", "Serena Hotel",      5);
        Hotel       h2  = new Hotel("H02", "Pearl Continental", 5);
        Person      g1  = new Person("G01", "Usman Malik", "usman@guide.com", "g1pass");
        Person      g2  = new Person("G02", "Nadia Iqbal", "nadia@guide.com", "g2pass");
        Dining      d1  = new Dining("D01", "Monal Restaurant",  "Pakistani");
        Dining      d2  = new Dining("D02", "Andaaz Restaurant", "Mughal");

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
import java.util.ArrayList;
import java.io.*;

public class Rating {

    private String ratingId;
    private String touristId;
    private ArrayList<Integer> score;
    private String comments;
    private String timestamp;
    private String targetType;

    static ArrayList<Rating> ratingList = new ArrayList<Rating>();

    public Rating(String ratingId, String touristId, ArrayList<Integer> score,String comments, String timestamp, String targetType) {
        this.ratingId   = ratingId;
        this.touristId  = touristId;
        this.score      = score;
        this.comments   = comments;
        this.timestamp  = timestamp;
        this.targetType = targetType;
    }

    public void addRating() {
        System.out.println("Rating added by: " + touristId + " for " + targetType);
    }

    public void updateComment(String comment) {
        this.comments = comment;
        System.out.println("Comment updated: " + comments);
    }

    public void submit() {
        System.out.println("Rating submitted | Scores: " + score + " | Target: " + targetType);
    }

    public double getAverage() {
        if (score.isEmpty()) return 0;
        int sum = 0;
        for (int i = 0; i < score.size(); i++) {
            sum += score.get(i);
        }
        double avg = (double) sum / score.size();
        System.out.printf("Average score for %s: %.2f%n", ratingId, avg);
        return avg;
    }

    public static void addToList(Rating r) {
        ratingList.add(r);
    }

    public static void removeRating(String id) {
        for (int i = 0; i < ratingList.size(); i++) {
            if (ratingList.get(i).ratingId.equals(id)) {
                ratingList.remove(i);
                System.out.println("Rating removed: " + id);
                return;
            }
        }
        System.out.println("Rating not found: " + id);
    }

    public static ArrayList<Rating> findByTourist(String touristId) {
        ArrayList<Rating> result = new ArrayList<Rating>();
        for (int i = 0; i < ratingList.size(); i++) {
            if (ratingList.get(i).touristId.equals(touristId))
                result.add(ratingList.get(i));
        }
        return result;
    }

    public static void displayAll() {
        System.out.println("\n--- Rating List ---");
        for (int i = 0; i < ratingList.size(); i++) {
            Rating r = ratingList.get(i);
            System.out.println("ID: " + r.ratingId + " | Tourist: " + r.touristId
                    + " | Target: " + r.targetType + " | Scores: " + r.score
                    + " | Comment: " + r.comments);
        }
        System.out.println("-------------------");
    }

    
    public static void saveToFile(String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < ratingList.size(); i++) {
                Rating r = ratingList.get(i);
                String scores = "";
                for (int j = 0; j < r.score.size(); j++) {
                    scores += r.score.get(j);
                    if (j < r.score.size() - 1) scores += ";";
                }
                bw.write(r.ratingId + "," + r.touristId + "," + scores + ","+ r.comments + "," + r.timestamp + "," + r.targetType);
                bw.newLine();
            }
            bw.close();
            System.out.println("Ratings saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public static void loadFromFile(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            ratingList.clear();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                ArrayList<Integer> scores = new ArrayList<Integer>();
                String[] scoreArr = parts[2].split(";");
                for (int i = 0; i < scoreArr.length; i++)
                    scores.add(Integer.parseInt(scoreArr[i]));
                ratingList.add(new Rating(parts[0], parts[1], scores, parts[3], parts[4], parts[5]));
            }
            br.close();
            System.out.println("Ratings loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        ArrayList<Integer> s1 = new ArrayList<Integer>();
        s1.add(5); s1.add(4); s1.add(5);

        ArrayList<Integer> s2 = new ArrayList<Integer>();
        s2.add(4); s2.add(3); s2.add(4);

        ArrayList<Integer> s3 = new ArrayList<Integer>();
        s3.add(5); s3.add(5);

        Rating r1 = new Rating("R001", "T001", s1, "Amazing experience!", "2024-01-10", "TourPackage");
        Rating r2 = new Rating("R002", "T002", s2, "Hotel was great!",    "2024-01-11", "Hotel");
        Rating r3 = new Rating("R003", "T001", s3, "Best food at Monal!", "2024-01-12", "Dining");

        addToList(r1);
        addToList(r2);
        addToList(r3);

        r1.addRating();
        r1.submit();
        r1.getAverage();

        r2.updateComment("Excellent hotel and service!");

        displayAll();

        saveToFile("ratings.txt");

        ratingList.clear();
        System.out.println("List cleared. Reloading...");
        loadFromFile("ratings.txt");
        displayAll();

        ArrayList<Rating> t1Ratings = findByTourist("T001");
        System.out.println("Ratings by T001: " + t1Ratings.size());

        removeRating("R002");
        displayAll();
        saveToFile("ratings.txt");
    }
}
