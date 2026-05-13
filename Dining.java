import java.io.*;
import java.util.ArrayList;

// Dining represents a restaurant/food option
// It is created inside TourPackage (composition) so it doesn't exist alone
// Implements Manageable interface (interface is someone else's task)
public class Dining implements Manageable, Serializable {

    private static final long serialVersionUID = 1L;

    // fields from UML
    private String diningID;
    private String restaurantName;
    private String cuisineType;
    private String priceRange;

    // menu items stored as a list (ArrayList usage)
    private ArrayList<String> menuItems;

    // transient: menu doesn't need to be saved to file every time
    // it can be reloaded or fetched fresh
    private transient ArrayList<String> dailySpecials;

    // --- Constructors ---

    // full constructor
    public Dining(String diningID, String restaurantName, String cuisineType, String priceRange) {
        this.diningID       = diningID;
        this.restaurantName = restaurantName;
        this.cuisineType    = cuisineType;
        this.priceRange     = priceRange;
        this.menuItems      = new ArrayList<>();
        this.dailySpecials  = new ArrayList<>();
    }

    // constructor chaining: simpler version with defaults
    public Dining(String diningID, String restaurantName) {
        this(diningID, restaurantName, "Mixed", "$$");
    }

    // --- Methods from UML ---

    @Override
    public void add() {
        System.out.println("Dining option added: " + restaurantName);
    }

    @Override
    public void view() {
        System.out.println(getDetails());
    }

    @Override
    public void delete() {
        System.out.println("Dining option removed: " + restaurantName);
    }

    @Override
    public String getDetails() {
        return "Dining[" + diningID + "] " + restaurantName +
               " | Cuisine: " + cuisineType +
               " | Price Range: " + priceRange;
    }

    // show the menu
    public void getMenu() {
        if (menuItems.isEmpty()) {
            System.out.println("No menu items listed for " + restaurantName);
            return;
        }
        System.out.println("Menu at " + restaurantName + " (" + cuisineType + "):");
        for (String item : menuItems) {
            System.out.println("  * " + item);
        }
    }

    // add a menu item
    public void addMenuItem(String item) {
        if (item == null || item.trim().isEmpty()) {
            throw new IllegalArgumentException("Menu item can't be empty."); // exception handling
        }
        menuItems.add(item);
    }

    // add a daily special (transient list, not saved to file)
    public void addDailySpecial(String special) {
        if (dailySpecials == null) {
            dailySpecials = new ArrayList<>(); // re-init after deserialization (transient)
        }
        dailySpecials.add(special);
    }

    // show daily specials
    public void showDailySpecials() {
        if (dailySpecials == null || dailySpecials.isEmpty()) {
            System.out.println("No specials today at " + restaurantName);
            return;
        }
        System.out.println("Today's specials at " + restaurantName + ":");
        for (String s : dailySpecials) {
            System.out.println("  ~ " + s);
        }
    }

    // save dining to file
    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename, true))) {
            out.writeObject(this);
            System.out.println("Dining saved: " + restaurantName);
        } catch (IOException e) {
            System.out.println("Error saving dining: " + e.getMessage());
        }
    }

    // load dining options from file
    public static ArrayList<Dining> loadFromFile(String filename) {
        ArrayList<Dining> list = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                Dining d = (Dining) in.readObject(); // downcasting
                list.add(d);
            }
        } catch (EOFException e) {
            // reached end of file, normal
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading dining: " + e.getMessage());
        }
        return list;
    }

    // --- Getters (encapsulation, no setters for ID - immutable-ish) ---
    public String getDiningID()       { return diningID; }
    public String getRestaurantName() { return restaurantName; }
    public String getCuisineType()    { return cuisineType; }
    public String getPriceRange()     { return priceRange; }

    // setters for things that can change
    public void setRestaurantName(String name) { this.restaurantName = name; }
    public void setCuisineType(String type)    { this.cuisineType = type; }
    public void setPriceRange(String range)    { this.priceRange = range; }
}
