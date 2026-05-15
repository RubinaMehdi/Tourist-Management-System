package touristPackage;

import java.io.*;
import java.util.ArrayList;

/**
 * Represents a restaurant / dining option linked to a TourPackage.
 * Composition  : TourPackage owns a Dining object (created and destroyed with the package)
 * Interface    : implements Manageable, Serializable
 * Encapsulation: fields private, menu items accessed through methods
 */
public class Dining implements Manageable, Serializable {
    private static final long serialVersionUID = 1L;

    private String diningID;
    private String restaurantName;
    private String cuisineType;
    private String priceRange;
    private ArrayList<String> menuItems;

    // Not persisted — rebuilt at runtime
    private transient ArrayList<String> dailySpecials;

    // ── Constructors ──────────────────────────────────────────────────────────
    public Dining(String diningID, String restaurantName, String cuisineType, String priceRange) {
        this.diningID        = diningID;
        this.restaurantName  = restaurantName;
        this.cuisineType     = cuisineType;
        this.priceRange      = priceRange;
        this.menuItems       = new ArrayList<>();
        this.dailySpecials   = new ArrayList<>();
    }

    public Dining(String diningID, String restaurantName) {
        this(diningID, restaurantName, "Mixed", "$$");
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getDiningID()        { return diningID; }
    public String getRestaurantName()  { return restaurantName; }
    public String getCuisineType()     { return cuisineType; }
    public String getPriceRange()      { return priceRange; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setRestaurantName(String name) {
        if (name != null && !name.trim().isEmpty()) this.restaurantName = name;
    }

    // ── Business methods ─────────────────────────────────────────────────────
    public void addMenuItem(String item) {
        if (item == null || item.trim().isEmpty())
            throw new IllegalArgumentException("Menu item cannot be empty.");
        menuItems.add(item.trim());
    }

    public void getMenu() {
        if (menuItems.isEmpty()) {
            System.out.println("  No menu items available.");
        } else {
            for (String item : menuItems) System.out.println("  * " + item);
        }
    }

    // ── Manageable interface (polymorphism) ────────────────────────────────────
    @Override public void add()    { System.out.println("Dining added: " + restaurantName); }
    @Override public void view()   { System.out.println(getDetails()); }
    @Override public void delete() { System.out.println("Dining removed: " + restaurantName); }

    @Override
    public String getDetails() {
        return "Dining[" + diningID + "] " + restaurantName
               + " | Cuisine: " + cuisineType + " | Price: " + priceRange;
    }

    @Override
    public String toString() { return restaurantName + " (" + cuisineType + ")"; }
}
