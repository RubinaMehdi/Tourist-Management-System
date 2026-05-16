package model;

import interfaces.*;

import java.util.ArrayList;
import java.util.List;

class Tourist extends Person implements Displayable {
    private String nationality;
    private String passportNumber;
    private String contactNumber;
    private int groupSize;
    // GENERIC PREFERENCE
    private ArrayList<String> preferences;
    // STATIC VARIABLE
    private static int touristCount = 0;
    // STATIC FINAL VARIABLE
    private static final String ROLE = "Tourist";

    // CONSTRUCTOR
    public Tourist(String personID, String name, String email, String password, String nationality,
            String passportNumber, String contactNumber, int groupSize) {
        super(personID, name, email, password);
        this.nationality = nationality;
        this.passportNumber = passportNumber;
        this.contactNumber = contactNumber;
        this.groupSize = groupSize;

        preferences = new ArrayList<>();
        touristCount++;
    }

    // GETTERS
    public String getNationality() {
        return nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public ArrayList<String> getPreferences() {
        return preferences;
    }

    // SETTERS
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public void setPreferences(ArrayList<String> preferences) {
        this.preferences = preferences;
    }

    // ADD PREFERENCE
    public void addPreference(String preference) {
        preferences.add(preference);
        System.out.println("Preference added: " + preference);
    }

    // VIEW PREFERENCE
    public void viewPreferences() {
        if (preferences.isEmpty()) {
            System.out.println("No preferences added yet.");
        } else {
            System.out.println("==== PREFERENCES ====");
            for (String preference : preferences) {
                System.out.println(" " + preference);
            }
        }
    }

    // UPDATE PREFERENCE
    public void updatePreference(int index, String newPreference) {
        if (index >= 0 && index < preferences.size()) {
            preferences.set(index, newPreference);
            System.out.println("Preference updated at index " + index + ": " + newPreference);
        } else {
            System.out.println("Invalid index. No preference updated.");
        }
    }

    // DELETE PREFERENCE
    public void deletePreference(String preference) {
        if (preferences.remove(preference)) {
            System.out.println("Preference removed: " + preference);
        } else {
            System.out.println("Preference not found.");
        }
    }

    // VIEW PACKAGES
    public void viewPackages(List<String> packages) {
        if (packages.isEmpty()) {
            System.out.println("No Packages Available.");
            return;
        }

        System.out.println("==== TOUR PACKAGES ====");
        for (String p : packages) {
            System.out.println(" " + p);
        }
    }

    // BOOK TOUR
    public void bookTour(String packageName) {
        System.out.println("Booking tour package: " + packageName);
    }

    // VIEW BOOKINGS
    public void viewBookings(List<String> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        System.out.println("==== BOOKING HISTORY ====");
        for (String b : bookings) {
            System.out.println(" " + b);
        }
    }

    // MAKE PAYMENT
    public void makePayment(double amount) {
        System.out.println("Payment of $" + amount + " successful!!!!");
    }

    // MANAGE DESTINATION
    public void manageDestination(String destination) {
        System.out.println("Destination Selected:" + destination);
    }

    // TOUR GUIDE
    public void tourGuide(String guideName) {
        System.out.println("Your assigned tour guide is: " + guideName);
    }

    // RATING
    public void giveRating(int stars, String feedback) {
        System.out.println("Rating: " + stars + " / 5 stars");
        System.out.println("Feedback: " + feedback);
        System.out.println("Rating submitted successfully!!!!");
    }

    // DISPLAY DETAILS
    @Override
    public void displayDetails() {
        System.out.println("==== TOURIST DETAILS ====");
        System.out.println("ID: " + getPersonID());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Contact Number: " + getContactNumber());
        System.out.println("Group Size: " + getGroupSize());
        System.out.println("Nationality: " + getNationality());
        System.out.println("Passport Number: " + getPassportNumber());
    }

    // ABSTRACT METHOD
    @Override
    public void showMenu() {
        System.out.println("==== TOURIST MENU ====");
        System.out.println("1. View Tour Packages");
        System.out.println("2. Book a Tour");
        System.out.println("3. View Bookings");
        System.out.println("4. Make Payment");
        System.out.println("5. Manage Destinations");
        System.out.println("6. See Tour Guide");
        System.out.println("7. Give Rating");
    }

    @Override
    public void displayRole() {
        System.out.println("Role: " + ROLE);
    }
}
