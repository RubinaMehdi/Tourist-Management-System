import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Destination implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributes (UML Diagram ke mutabiq)
    private String destinationID;
    private String destinationName;
    private String country;
    private String description;
    private List<String> attractions; // UML: Attractions: list
    private double rating;

    // Static ArrayList: Tamam destinations ko memory me manage karne ke liye
    private static ArrayList<Destination> destinationList = new ArrayList<>();
    private static final String FILE_NAME = "destinations.txt";

    // Constructor
    public Destination(String destinationID, String destinationName, String country, String description,
            double rating) {
        this.destinationID = destinationID;
        this.destinationName = destinationName;
        this.country = country;
        this.description = description;
        this.attractions = new ArrayList<>();
        this.rating = rating;
    }

    // Getters aur Setters
    public String getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAttractions() {
        return attractions;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void addAttraction(String attraction) {
        this.attractions.add(attraction);
    }

    // UML Methods & File Handling Implementation
    public void add() {
        destinationList.add(this);
        saveToFile();
        System.out.println("Destination successfully added and saved.");
    }

    public void view() {
        System.out.println("\n--- Destination Details ---");
        System.out.println("ID: " + destinationID);
        System.out.println("Name: " + destinationName);
        System.out.println("Country: " + country);
        System.out.println("Description: " + description);
        System.out.println("Attractions: " + attractions);
        System.out.println("Rating: " + rating + " Stars");
    }

    public void delete() {
        destinationList.remove(this);
        saveToFile();
        System.out.println("Destination deleted from system.");
    }

    public String getDetails() {
        return "ID: " + destinationID + " | Name: " + destinationName + " (" + country + ") | Rating: " + rating;
    }

    // File Handling: ArrayList ko storage me save karna
    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(destinationList);
        } catch (IOException e) {
            System.err.println("Error saving destinations: " + e.getMessage());
        }
    }

    // File Handling: Storage sy data wapas load karna
    @SuppressWarnings("unchecked")
    public static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            destinationList = (ArrayList<Destination>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading destinations: " + e.getMessage());
        }
    }
}
