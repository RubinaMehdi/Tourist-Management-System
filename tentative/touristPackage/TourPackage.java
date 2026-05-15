package touristPackage;

import java.io.*;
import java.util.ArrayList;

/**
 * Represents a complete tour package offered to tourists.
 * Interface    : implements Manageable, Serializable
 * Composition  : owns a Dining object (created inside this class — composition)
 * Aggregation  : references Hotel, TourGuide, Destination (exist independently)
 * File handling: static list is persisted to packages.dat
 * Encapsulation: all fields private with getters/setters
 */
public class TourPackage implements Manageable, Serializable {
    private static final long serialVersionUID = 1L;

    private static ArrayList<TourPackage> packageList = new ArrayList<>();
    private static final String FILE_NAME = "packages.dat";

    private String  packageID;
    private String  title;
    private String  description;
    private double  price;
    private int     duration;
    private boolean diningIncluded;

    // Composition: Dining is created and owned by TourPackage
    private Dining dining;

    // Aggregation: Hotel, TourGuide, Destination exist independently
    private Hotel       hotel;
    private TourGuide   guide;
    private Destination destination;

    private ArrayList<String> extras;

    // ── Constructors ──────────────────────────────────────────────────────────
    public TourPackage(String packageID, String title, String description,
                       double price, int duration, boolean diningIncluded) {
        this.packageID      = packageID;
        this.title          = title;
        this.description    = description;
        this.price          = price;
        this.duration       = duration;
        this.diningIncluded = diningIncluded;
        this.extras         = new ArrayList<>();
        if (diningIncluded) {
            // Composition: Dining object is created as part of the package
            this.dining = new Dining("DIN-" + packageID, "Package Restaurant", "Local", "$$$");
        }
    }

    public TourPackage(String packageID, String title, double price, int duration) {
        this(packageID, title, "Standard package", price, duration, false);
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String      getPackageID()    { return packageID; }
    public String      getTitle()        { return title; }
    public String      getDescription()  { return description; }
    public double      getPrice()        { return price; }
    public int         getDuration()     { return duration; }
    public boolean     isDiningIncluded(){ return diningIncluded; }
    public Dining      getDining()       { return dining; }
    public Hotel       getHotel()        { return hotel; }
    public TourGuide   getGuide()        { return guide; }
    public Destination getDestination()  { return destination; }

    // ── Setters / association methods ─────────────────────────────────────────
    public void setHotel(Hotel hotel)             { this.hotel = hotel; }
    public void setDestination(Destination dest)  { this.destination = dest; }

    public void setGuide(TourGuide guide) {
        this.guide = guide;
        if (guide != null) guide.assignPackage(this);
    }

    public void setDining(String restaurantName, String cuisineType, String priceRange) {
        this.diningIncluded = true;
        this.dining = new Dining("DIN-" + packageID, restaurantName, cuisineType, priceRange);
    }

    public void addExtra(String extra) {
        if (extra != null && !extra.trim().isEmpty()) extras.add(extra.trim());
    }

    // ── Manageable interface (polymorphism) ────────────────────────────────────
    @Override
    public void add() {
        packageList.add(this);
        saveAllToFile();
    }

    @Override
    public void view() { System.out.println(getDetails()); }

    @Override
    public void delete() {
        packageList.remove(this);
        saveAllToFile();
    }

    @Override
    public String getDetails() {
        return "Package[" + packageID + "] " + title + " | " + duration + " days | $" + price
               + (diningIncluded ? " | Dining included" : "");
    }

    // ── File handling ─────────────────────────────────────────────────────────
    /** Saves only this package's list (same file — kept for backward compat). */
    public void saveToFile() {
        saveAllToFile();
    }

    /** Saves the entire static list once — avoids repeated overwrites. */
    private static void saveAllToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(packageList);
        } catch (IOException e) {
            System.err.println("Error saving packages: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            packageList = (ArrayList<TourPackage>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading packages: " + e.getMessage());
        }
    }

    // ── Static CRUD helpers ───────────────────────────────────────────────────
    public static ArrayList<TourPackage> getPackageList() { return packageList; }

    public static void addPackage(TourPackage pkg) {
        packageList.add(pkg);
        saveAllToFile();
    }

    public static void removePackage(String id) {
        packageList.removeIf(p -> p.packageID.equals(id));
        saveAllToFile();
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    public String getDisplayString() {
        return packageID + " - " + title + " ($" + price + ", " + duration + " days)";
    }

    @Override
    public String toString() { return getDisplayString(); }
}
