package touristPackage;

import java.io.*;
import java.util.ArrayList;

/**
 * Represents a tour guide in the system.
 * Inheritance  : extends Person
 * Interfaces   : implements Manageable, Serializable
 * Composition  : has-a list of TourPackage (assignedPackages)
 * Association  : has-a list of Tourist (assignedTourists) — transient so it is not persisted
 * Encapsulation: all fields private
 */
public class TourGuide extends Person implements Manageable, Serializable {
    private static final long serialVersionUID = 1L;

    private String  guideID;
    private String  language;
    private String  expertise;
    private boolean available;
    private ArrayList<TourPackage> assignedPackages;

    // Transient: rebuilt at runtime, not saved to disk (avoids circular serialization)
    private transient ArrayList<Tourist> assignedTourists;

    // ── Constructor ───────────────────────────────────────────────────────────
    public TourGuide(String personID, String name, String email, String password,
                     String guideID, String language, String expertise) {
        super(personID, name, email, password);
        this.guideID           = guideID;
        this.language          = language;
        this.expertise         = expertise;
        this.available         = true;
        this.assignedPackages  = new ArrayList<>();
        this.assignedTourists  = new ArrayList<>();
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String  getGuideID()   { return guideID; }
    public String  getLanguage()  { return language; }
    public String  getExpertise() { return expertise; }
    public boolean isAvailable()  { return available; }
    public ArrayList<TourPackage> getAssignedPackages() { return assignedPackages; }

    // ── Business methods ─────────────────────────────────────────────────────
    public void updateAvailability(boolean status) { this.available = status; }

    public void assignPackage(TourPackage pkg) {
        if (pkg != null && !assignedPackages.contains(pkg))
            assignedPackages.add(pkg);
    }

    public void addTourist(Tourist t) {
        if (assignedTourists == null) assignedTourists = new ArrayList<>();
        if (t != null) assignedTourists.add(t);
    }

    public void viewAssignedTours() {
        if (assignedPackages.isEmpty()) {
            System.out.println("  No packages assigned.");
        } else {
            for (TourPackage p : assignedPackages)
                System.out.println("  - " + p.getTitle());
        }
    }

    // ── Manageable interface (polymorphism) ────────────────────────────────────
    @Override public void add()    { System.out.println("Guide added: " + name); }
    @Override public void view()   { System.out.println(getDetails()); }
    @Override public void delete() { System.out.println("Guide removed: " + name); }

    @Override
    public String getDetails() {
        return "Guide[" + guideID + "] " + name + " | " + language + " | " + expertise
               + " | " + (available ? "Available" : "Busy");
    }

    // ── Person abstract methods (polymorphism) ─────────────────────────────────
    @Override
    public String getProfile() {
        return super.getProfile() + " | Role: Tour Guide | Expertise: " + expertise;
    }

    @Override public void showMenu()     {}
    @Override public void displayRole()  { System.out.println("Role: Tour Guide"); }
    @Override public void displayDetails() { System.out.println(getDetails()); }

    // ── Helper ────────────────────────────────────────────────────────────────
    public String getDisplayString() {
        return guideID + " - " + name + " (" + language + " / " + expertise + ")";
    }

    @Override
    public String toString() { return getDisplayString(); }
}
