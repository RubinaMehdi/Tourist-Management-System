package model;

import interfaces.*;
import java.io.*;
import java.util.ArrayList;
interface IAdminOperations<T> {
    void addRecord(T record);
    ArrayList<T> getAllRecords();
}
// IAdminOperations implement interface
// Aggregation: Contain all classes lists 
public class Admin extends Person implements IAdminOperations<String> {
    private int adminLevel;
    private String department;

    // Generics and ArrayList 
    private ArrayList<String> logStorage;
    private ArrayList<Hotel> hotels;
    private ArrayList<TourPackage> packages;
    private ArrayList<Tourist> tourists;
    private ArrayList<TourGuide> guides;
    private ArrayList<Booking> bookings;
    private ArrayList<Transport> transports;
    public Admin(int adminLevel, String department) {
        super("A001", "Admin", "admin@tourism.com", "admin123");
        this.adminLevel  = adminLevel;
        this.department  = department;
        this.logStorage  = new ArrayList<>();
        this.hotels      = new ArrayList<>();
        this.packages    = new ArrayList<>();
        this.tourists    = new ArrayList<>();
        this.guides      = new ArrayList<>();
        this.bookings    = new ArrayList<>();
        this.transports  = new ArrayList<>();
    }
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

    public ArrayList<Hotel>       getHotels()     { return hotels; }
    public ArrayList<TourPackage> getPackages()   { return packages; }
    public ArrayList<Tourist>     getTourists()   { return tourists; }
    public ArrayList<TourGuide>   getGuides()     { return guides; }
    public ArrayList<Booking>     getBookings()   { return bookings; }
    public ArrayList<Transport>   getTransports() { return transports; }

    // Setters for lists
    public void setHotels(ArrayList<Hotel> h)         { this.hotels = h; }
    public void setPackages(ArrayList<TourPackage> p) { this.packages = p; }
    public void setTourists(ArrayList<Tourist> t)     { this.tourists = t; }
    public void setGuides(ArrayList<TourGuide> g)     { this.guides = g; }
    public void setBookings(ArrayList<Booking> b)     { this.bookings = b; }
    public void setTransports(ArrayList<Transport> t) { this.transports = t; }

    
    @Override
    public void addRecord(String record) {
        logStorage.add(record);
        System.out.println("[Log Added]: " + record);
    }

    @Override
    public ArrayList<String> getAllRecords() {
        return this.logStorage;
    }

    // FILE HANDLING
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

    // File Handling: Data read 
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

    // Hotel class interact 
    public void manageHotels() {
        addRecord("Admin (Level " + adminLevel + ") managed hotel listings.");
        System.out.println("=== Managing Hotels ===");
        for (Hotel h : hotels) {
            h.displayDetails();
        }
        saveToFile("admin_logs.txt");
    }

    // TourPackage class se interact 
    public void managePackages() {
        addRecord("Admin (Level " + adminLevel + ") modified tour packages for " + department + " department.");
        System.out.println("=== Managing Tour Packages ===");
        for (TourPackage p : packages) {
            p.displayDetails();
        }
        saveToFile("admin_logs.txt");
    }

    // Tourist class se interact 
    public void manageTourists() {
        addRecord("Admin updated tourist verification statuses.");
        System.out.println("=== Managing Tourists ===");
        for (Tourist t : tourists) {
            t.displayDetails();
        }
        saveToFile("admin_logs.txt");
    }

    // TourGuide class se interact
    public void manageTourGuides() {
        addRecord("Admin assigned tasks to active tour guides.");
        System.out.println("=== Managing Tour Guides ===");
        for (TourGuide g : guides) {
            g.displayDetails();
        }
        saveToFile("admin_logs.txt");
    }

    // Booking class se interact 
    public void manageBooking() {
        addRecord("Admin processed and approved pending reservations.");
        System.out.println("=== Managing Bookings ===");
        for (Booking b : bookings) {
            b.displayDetails();
        }
        saveToFile("admin_logs.txt");
    }

    // Transport class se interact 
    public void manageTransport() {
        addRecord("Admin updated transport schedules and bus routes.");
        System.out.println("=== Managing Transport ===");
        for (Transport t : transports) {
            t.displayDetails();
        }
        saveToFile("admin_logs.txt");
    }

    // ABSTRACT METHODS FROM PERSON (Polymorphism)
    @Override
    public void showMenu() {
        System.out.println("=== Admin Menu ===");
        System.out.println("1. Manage Packages");
        System.out.println("2. Manage Hotels");
        System.out.println("3. Manage Tourists");
        System.out.println("4. Manage Tour Guides");
        System.out.println("5. Manage Bookings");
        System.out.println("6. Manage Transport");
    }

    @Override
    public void displayRole() {
        System.out.println("Role       : Admin");
        System.out.println("Level      : " + adminLevel);
        System.out.println("Department : " + department);
    }

    @Override
    public void displayDetails() {
        System.out.println("Admin Level  : " + adminLevel);
        System.out.println("Department   : " + department);
        System.out.println("Total Logs   : " + logStorage.size());
    }

    // =====================================================
    // MAIN METHOD - Code test karne ke liye
    // =====================================================
    public static void main(String[] args) {
        String dbFile = "admin_records.txt";

        // Admin object banana
        Admin systemAdmin = new Admin(5, "Operations");

        // File load if already exist
        systemAdmin.loadFromFile(dbFile);

        System.out.println("\n--- Performing Admin Actions ---");

        systemAdmin.managePackages();
        systemAdmin.manageHotels();
        systemAdmin.manageTourists();
        systemAdmin.manageTourGuides();
        systemAdmin.manageBooking();
        systemAdmin.manageTransport();

        // Data Permanant storage
        
        systemAdmin.saveToFile(dbFile);
    }
}
