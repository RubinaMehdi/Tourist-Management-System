package touristPackage;

import java.io.Serializable;

/**
 * Represents a hotel that can be linked to a TourPackage.
 * Interface    : implements Displayable, Serializable
 * Encapsulation: all fields private with validated setters
 * Association  : used by TourPackage (aggregation — hotel exists independently)
 */
public class Hotel implements Displayable, Serializable {
    private static final long serialVersionUID = 1L;

    private String hotelID;
    private String name;
    private String location;
    private int    starRating;
    private double pricePerNight;
    private int    availableRooms;

    public static int    hotelCount = 0;
    public static final double TAX_RATE = 0.15;

    // ── Constructor ───────────────────────────────────────────────────────────
    public Hotel(String hotelID, String name, String location,
                 int starRating, double pricePerNight, int availableRooms) {
        this.hotelID        = hotelID;
        this.name           = name;
        this.location       = location;
        this.starRating     = starRating;
        this.pricePerNight  = pricePerNight;
        this.availableRooms = availableRooms;
        hotelCount++;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getHotelID()        { return hotelID; }
    public String getName()           { return name; }
    public String getLocation()       { return location; }
    public int    getStarRating()     { return starRating; }
    public double getPricePerNight()  { return pricePerNight; }
    public int    getAvailableRooms() { return availableRooms; }

    // ── Setters with validation ───────────────────────────────────────────────
    public void setName(String name)          { if (name != null && !name.isEmpty()) this.name = name; }
    public void setLocation(String location)  { if (location != null && !location.isEmpty()) this.location = location; }
    public void setStarRating(int starRating) { if (starRating >= 1 && starRating <= 5) this.starRating = starRating; }
    public void setPricePerNight(double p)    { if (p > 0) this.pricePerNight = p; }
    public void setAvailableRooms(int rooms)  { if (rooms >= 0) this.availableRooms = rooms; }

    // ── Business methods ─────────────────────────────────────────────────────
    public void addHotel()    { System.out.println("Hotel added: " + name); }
    public void viewHotel()   { System.out.println(getDetails()); }
    public void deleteHotel() { System.out.println("Hotel deleted: " + name); }

    public boolean checkAvailability(int roomsRequested) {
        return availableRooms >= roomsRequested;
    }

    /**
     * Calculates total price including tax.
     * @throws IllegalArgumentException if nights <= 0
     */
    public double calculateTotalPrice(int nights) {
        if (nights <= 0) throw new IllegalArgumentException("Number of nights must be positive.");
        return (pricePerNight * nights) * (1 + TAX_RATE);
    }

    public String getDetails() {
        return "Hotel: " + name + " | " + location + " | " + starRating
               + " stars | $" + pricePerNight + "/night | Rooms: " + availableRooms;
    }

    // ── Interface implementation (polymorphism) ────────────────────────────────
    @Override
    public void displayDetails() { viewHotel(); }

    @Override
    public String toString() { return name + " (" + starRating + "★, " + location + ")"; }
}
