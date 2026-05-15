package touristPackage;

import java.io.Serializable;

/**
 * Represents a transport option (bus, train, van, etc.).
 * Interface    : implements Displayable, Serializable
 * Encapsulation: all fields private with validated setters
 */
public class Transport implements Displayable, Serializable {
    private static final long serialVersionUID = 1L;

    private String transportID;
    private String transportType;
    private int    capacity;
    private String routeFrom;
    private String routeTo;

    public static int transportCount = 0;
    public static final int MAX_CAPACITY = 50;

    // ── Constructor ───────────────────────────────────────────────────────────
    public Transport(String transportID, String transportType,
                     int capacity, String routeFrom, String routeTo) {
        this.transportID   = transportID;
        this.transportType = transportType;
        this.capacity      = capacity;
        this.routeFrom     = routeFrom;
        this.routeTo       = routeTo;
        transportCount++;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getTransportID()   { return transportID; }
    public String getTransportType() { return transportType; }
    public int    getCapacity()      { return capacity; }
    public String getRouteFrom()     { return routeFrom; }
    public String getRouteTo()       { return routeTo; }

    // ── Setters with validation ───────────────────────────────────────────────
    public void setTransportType(String type) { if (type != null && !type.isEmpty()) this.transportType = type; }
    public void setCapacity(int capacity)     { if (capacity > 0 && capacity <= MAX_CAPACITY) this.capacity = capacity; }

    // ── Business methods ─────────────────────────────────────────────────────
    public void addTransport()    { System.out.println("Transport added: " + transportType); }
    public void viewTransport()   { System.out.println(getDetails()); }
    public void deleteTransport() { System.out.println("Transport deleted: " + transportType); }

    public boolean checkAvailability(int requestedSeats) { return capacity >= requestedSeats; }

    public String getDetails() {
        return transportType + " | " + routeFrom + " → " + routeTo + " | Capacity: " + capacity;
    }

    // ── Interface implementation (polymorphism) ────────────────────────────────
    @Override
    public void displayDetails() { viewTransport(); }

    @Override
    public String toString() { return transportType + " (" + routeFrom + " → " + routeTo + ")"; }
}
