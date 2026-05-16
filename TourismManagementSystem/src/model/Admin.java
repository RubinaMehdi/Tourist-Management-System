package model;

import interfaces.*;

import java.io.*;
import java.util.ArrayList;

interface IAdminOperations<T> {
    void addRecord(T record);

    ArrayList<T> getAllRecords();
}

class Admin implements IAdminOperations<String> {
    // Attributes
    private int adminLevel;
    private String department;

    // Generics aur ArrayList ka istemal data store karne ke liye
    private ArrayList<String> logStorage;

    // Constructor
    public Admin(int adminLevel, String department) {
        this.adminLevel = adminLevel;
        this.department = department;
        this.logStorage = new ArrayList<>();
    }

    // Getters and Setters
    public int getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(int adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Interface Methods ki Implementation
    public void addRecord(String record) {
        logStorage.add(record);
        System.out.println("[Log Added]: " + record);
    }

    public ArrayList<String> getAllRecords() {
        return this.logStorage;
    }

    // File Handling: Data ko txt file me save karna
    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (String log : logStorage) {
                writer.write(log);
                writer.newLine();
            }
            System.out.println("--- Data successfully saved to " + fileName + " ---");
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    // File Handling: Data ko txt file sy read karna
    public void loadFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("--- Reading Logs From File ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing record file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    // UML Diagram ke Mutabiq Methods (Ab functional hain)
    public void managePackages() {
        addRecord("Admin (Level " + adminLevel + ") modified tour packages for " + department + " department.");
    }

    public void manageHotels() {
        addRecord("Admin managed hotel listings and updated room pricing.");
    }

    public void manageTourists() {
        addRecord("Admin updated tourist verification statuses.");
    }

    public void manageTourGuides() {
        addRecord("Admin assigned tasks to active tour guides.");
    }

    public void manageBooking() {
        addRecord("Admin processed and approved pending reservations.");
    }

    public void manageTransport() {
        addRecord("Admin updated transport schedules and bus routes.");
    }

    public void manageDining() {
        addRecord("Admin updated hotel dining menus and meal plans.");
    }

    // Code Test karne ke liye Main Method
    public static void main(String[] args) {
        String dbFile = "admin_records.txt";

        // Admin Object banana
        Admin systemAdmin = new Admin(5, "Operations");

        // Purana data load karna (agar file pehle sy majood ho)
        systemAdmin.loadFromFile(dbFile);

        System.out.println("\n--- Performing Admin Actions ---");
        // Diagram wale actions run karna
        systemAdmin.managePackages();
        systemAdmin.manageHotels();
        systemAdmin.manageBooking();

        // Data ko permanent file me save karna
        systemAdmin.saveToFile(dbFile);
    }
}
