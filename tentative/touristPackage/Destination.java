package touristPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a travel destination.
 * Serializable : stored to destinations.dat
 * Encapsulation: fields private, exposed via getters/setters
 * File handling: static load/save methods manage the shared destination list
 */
public class Destination implements Serializable {
    private static final long serialVersionUID = 1L;

    private static ArrayList<Destination> destinationList = new ArrayList<>();
    private static final String FILE_NAME = "destinations.dat";

    private String destinationID;
    private String destinationName;
    private String country;
    private String description;
    private List<String> attractions;
    private double rating;

    // ── Constructor ───────────────────────────────────────────────────────────
    public Destination(String destinationID, String destinationName, String country, String description) {
        this.destinationID   = destinationID;
        this.destinationName = destinationName;
        this.country         = country;
        this.description     = description;
        this.attractions     = new ArrayList<>();
        this.rating          = 0.0;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String       getDestinationID()   { return destinationID; }
    public String       getDestinationName() { return destinationName; }
    public String       getCountry()         { return country; }
    public String       getDescription()     { return description; }
    public List<String> getAttractions()     { return attractions; }
    public double       getRating()          { return rating; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setRating(double rating) {
        if (rating >= 0.0 && rating <= 5.0) this.rating = rating;
    }

    public void addAttraction(String attraction) {
        if (attraction != null && !attraction.trim().isEmpty())
            this.attractions.add(attraction.trim());
    }

    // ── Instance CRUD methods ─────────────────────────────────────────────────
    public void add() {
        destinationList.add(this);
        saveToFile();
    }

    public void view() {
        System.out.println(getDetails());
    }

    public void delete() {
        destinationList.remove(this);
        saveToFile();
    }

    public String getDetails() {
        return "ID: " + destinationID + " | " + destinationName + " (" + country + ")";
    }

    // ── File handling ─────────────────────────────────────────────────────────
    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(destinationList);
        } catch (IOException e) {
            System.err.println("Error saving destinations: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            destinationList = (ArrayList<Destination>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading destinations: " + e.getMessage());
        }
    }

    public static ArrayList<Destination> getDestinationList() { return destinationList; }

    @Override
    public String toString() {
        return destinationID + " - " + destinationName + " (" + country + ")";
    }
}
