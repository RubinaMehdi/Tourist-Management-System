package model;

import interfaces.*;

import java.io.*;
import java.util.ArrayList;

// TourPackage holds all services in one trip
// It has composition with Dining (dining is created inside the package)
// It has aggregation with Hotel, TourGuide, Destination (they exist independently)
// Implements Manageable interface (interface is someone else's task)
public class TourPackage implements Manageable, Serializable {

    private static final long serialVersionUID = 1L;

    // fields from UML
    private String packageID;
    private String title;
    private String description;
    private double price;
    private int duration; // in days

    // whether dining is included in this package
    private boolean diningIncluded;

    // composition: Dining is created and owned by this package
    // if package is deleted, the dining option goes with it
    private Dining dining;

    // aggregation: these exist on their own, package just references them
    private Hotel hotel;
    private TourGuide guide;
    private Destination destination;

    // generic list of extras (generics usage)
    private ArrayList<String> extras; // e.g. "Museum visit", "Safari"

    // --- Constructors ---

    // full constructor
    public TourPackage(String packageID, String title, String description,
            double price, int duration, boolean diningIncluded) {
        this.packageID = packageID;
        this.title = title;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.diningIncluded = diningIncluded;
        this.extras = new ArrayList<>();

        // composition: dining created here if included
        if (diningIncluded) {
            this.dining = new Dining("DIN-" + packageID, "Package Restaurant", "Local", "$$$");
        }
    }

    // shorter constructor that chains to full one
    public TourPackage(String packageID, String title, double price, int duration) {
        this(packageID, title, "Standard package", price, duration, false);
    }

    // --- Methods from UML ---

    public void add() {
        System.out.println("Package added: " + title);
    }

    public void view() {
        System.out.println(getDetails());
    }

    public void delete() {
        System.out.println("Package removed: " + title);
    }

    public String getDetails() {
        return "Package[" + packageID + "] " + title +
                " | " + duration + " days" +
                " | Price: $" + price +
                " | Dining: " + (diningIncluded ? "Yes" : "No") +
                (hotel != null ? " | Hotel: " + hotel.getName() : "") +
                (destination != null ? " | Dest: " + destination.getDestinationName() : "");
    }

    // add an extra activity
    public void addExtra(String extra) {
        extras.add(extra);
        System.out.println("Added extra to " + title + ": " + extra);
    }

    // attach an existing hotel to this package (aggregation)
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    // attach an existing guide (aggregation)
    public void setGuide(TourGuide guide) {
        this.guide = guide;
        guide.assignPackage(this); // tell the guide too (association both ways)
    }

    // attach destination (aggregation)
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    // replace the dining option (re-creates it, still composition)
    public void setDining(String restaurantName, String cuisineType, String priceRange) {
        this.diningIncluded = true;
        this.dining = new Dining("DIN-" + packageID, restaurantName, cuisineType, priceRange);
    }

    // save this package to file
    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename, true))) {
            out.writeObject(this);
            System.out.println("Package saved: " + title);
        } catch (IOException e) {
            System.out.println("Error saving package: " + e.getMessage());
        }
    }

    // load all packages from file
    public static ArrayList<TourPackage> loadFromFile(String filename) {
        ArrayList<TourPackage> list = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                TourPackage p = (TourPackage) in.readObject(); // downcasting
                list.add(p);
            }
        } catch (EOFException e) {
            // end of file, fine
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading packages: " + e.getMessage());
        }
        return list;
    }

    // --- Getters (encapsulation) ---
    public String getPackageID() {
        return packageID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isDiningIncluded() {
        return diningIncluded;
    }

    public Dining getDining() {
        return dining;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public TourGuide getGuide() {
        return guide;
    }

    public Destination getDestination() {
        return destination;
    }

    public ArrayList<String> getExtras() {
        return extras;
    }
}
