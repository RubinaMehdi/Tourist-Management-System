package touristPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a tourist / customer in the system.
 * Inheritance  : extends Person
 * Interface    : implements Displayable
 * Serializable : needed so tourists can be saved to file via ObjectOutputStream
 * Encapsulation: all fields private, accessed through validated getters/setters
 */
public class Tourist extends Person implements Displayable, Serializable {
    private static final long serialVersionUID = 1L;

    private String nationality;
    private String passportNumber;
    private String contactNumber;
    private int    groupSize;
    private ArrayList<String> preferences;

    private static int    touristCount = 0;
    private static final String ROLE  = "Tourist";

    // ── Constructor ───────────────────────────────────────────────────────────
    public Tourist(String personID, String name, String email, String password,
                   String nationality, String passportNumber,
                   String contactNumber, int groupSize) {
        super(personID, name, email, password);
        this.nationality    = nationality;
        this.passportNumber = passportNumber;
        this.contactNumber  = contactNumber;
        this.groupSize      = groupSize;
        this.preferences    = new ArrayList<>();
        touristCount++;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getNationality()    { return nationality; }
    public String getPassportNumber() { return passportNumber; }
    public String getContactNumber()  { return contactNumber; }
    public int    getGroupSize()      { return groupSize; }
    public ArrayList<String> getPreferences() { return preferences; }
    public static int getTouristCount()       { return touristCount; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setNationality(String nationality)     { this.nationality    = nationality; }
    public void setContactNumber(String contactNumber) { this.contactNumber  = contactNumber; }
    public void setGroupSize(int groupSize) {
        if (groupSize > 0) this.groupSize = groupSize;
    }

    // ── Preference management ─────────────────────────────────────────────────
    public void addPreference(String preference) {
        if (preference != null && !preference.trim().isEmpty())
            preferences.add(preference.trim());
    }

    public void viewPreferences() {
        if (preferences.isEmpty()) {
            System.out.println("No preferences added.");
        } else {
            for (String p : preferences) System.out.println(" - " + p);
        }
    }

    public void updatePreference(int index, String newPreference) {
        if (index >= 0 && index < preferences.size() && newPreference != null)
            preferences.set(index, newPreference.trim());
    }

    public void deletePreference(String preference) {
        preferences.remove(preference);
    }

    // ── Tour / payment actions ────────────────────────────────────────────────
    public void viewPackages(List<String> packages) {
        for (String p : packages) System.out.println(" - " + p);
    }

    public void bookTour(String packageName) {
        System.out.println(name + " booked: " + packageName);
    }

    public void makePayment(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Payment amount must be positive.");
        System.out.println("Payment of $" + amount + " by " + name + " successful.");
    }

    public void giveRating(int stars, String feedback) {
        if (stars < 1 || stars > 5) throw new IllegalArgumentException("Rating must be between 1 and 5.");
        System.out.println("Rating: " + stars + "/5 - " + feedback);
    }

    // ── Interface implementations (polymorphism) ───────────────────────────────
    @Override
    public void displayDetails() {
        System.out.println("Tourist: " + name + " | " + nationality + " | Contact: " + contactNumber);
    }

    @Override
    public void showMenu() {
        System.out.println("1. View Preferences\n2. Add Preference\n3. Book Tour\n4. Make Payment");
    }

    @Override
    public void displayRole() {
        System.out.println("Role: " + ROLE);
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    public String getDisplayString() {
        return personID + " - " + name + " (" + nationality + ")";
    }

    @Override
    public String toString() {
        return getDisplayString();
    }
}
