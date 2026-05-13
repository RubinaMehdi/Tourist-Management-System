import java.io.*;
import java.util.ArrayList;

// TourGuide extends Person (inheritance)
// TourGuide is also Manageable (interface - someone else's task)
public class TourGuide extends Person implements Manageable, Serializable {

    private static final long serialVersionUID = 1L;

    // own fields
    private String guideID;
    private String language;
    private String expertise;
    private boolean available;

    // aggregation: guide is assigned to packages but doesn't own them
    private ArrayList<TourPackage> assignedPackages;

    // transient: we don't want to save tourists list to file (it changes too much)
    private transient ArrayList<Tourist> assignedTourists;

    // --- Constructors ---

    // basic constructor
    public TourGuide(String personID, String name, String email, String password,
                     String guideID, String language, String expertise) {
        super(personID, name, email, password); // constructor chaining to Person
        this.guideID = guideID;
        this.language = language;
        this.expertise = expertise;
        this.available = true;
        this.assignedPackages = new ArrayList<>();
        this.assignedTourists = new ArrayList<>();
    }

    // --- Methods from UML ---

    // show the packages this guide is assigned to
    public void viewAssignedTours() {
        if (assignedPackages.isEmpty()) {
            System.out.println(name + " has no assigned tours right now.");
            return;
        }
        System.out.println("Tours assigned to " + name + ":");
        for (TourPackage p : assignedPackages) {
            System.out.println("  - " + p.getTitle() + " (" + p.getDuration() + " days)");
        }
    }

    // toggle availability
    public void updateAvailability(boolean status) {
        this.available = status;
        System.out.println(name + " availability updated to: " + status);
    }

    // show tourists assigned to this guide
    public void viewTourists() {
        if (assignedTourists == null || assignedTourists.isEmpty()) {
            System.out.println("No tourists assigned to " + name + " yet.");
            return;
        }
        System.out.println("Tourists with guide " + name + ":");
        for (Tourist t : assignedTourists) {
            System.out.println("  - " + t.getName() + " (" + t.getNationality() + ")");
        }
    }

    // calculate and show average rating from a list of ratings
    public void viewRating(ArrayList<Rating> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            System.out.println("No ratings yet for " + name);
            return;
        }
        double total = 0;
        for (Rating r : ratings) {
            total += r.getScore();
        }
        double avg = total / ratings.size();
        System.out.printf("Average rating for %s: %.1f / 5.0%n", name, avg);
    }

    // add a package to this guide's list (aggregation - package exists on its own too)
    public void assignPackage(TourPackage pkg) {
        if (!assignedPackages.contains(pkg)) {
            assignedPackages.add(pkg);
        }
    }

    // add tourist to guide's list
    public void addTourist(Tourist t) {
        if (assignedTourists == null) {
            assignedTourists = new ArrayList<>();
        }
        assignedTourists.add(t);
    }

    // save guide info to a file (file handling)
    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename, true))) {
            out.writeObject(this);
            System.out.println("TourGuide saved: " + name);
        } catch (IOException e) {
            System.out.println("Error saving guide: " + e.getMessage());
        }
    }

    // load all guides from a file
    public static ArrayList<TourGuide> loadFromFile(String filename) {
        ArrayList<TourGuide> list = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                TourGuide g = (TourGuide) in.readObject(); // downcasting
                list.add(g);
            }
        } catch (EOFException e) {
            // reached end of file, normal
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading guides: " + e.getMessage());
        }
        return list;
    }

    // --- Manageable interface methods (interface is someone else's task) ---
    // These are what should go in the Manageable interface:
    //   void add();
    //   void view();
    //   void delete();
    //   String getDetails();

    @Override
    public void add() {
        System.out.println("Guide added: " + name);
    }

    @Override
    public void view() {
        System.out.println(getDetails());
    }

    @Override
    public void delete() {
        System.out.println("Guide removed: " + name);
    }

    @Override
    public String getDetails() {
        return "Guide[" + guideID + "] " + name +
               " | Language: " + language +
               " | Expertise: " + expertise +
               " | Available: " + available;
    }

    // --- Getters (encapsulation) ---
    public String getGuideID()   { return guideID; }
    public String getLanguage()  { return language; }
    public String getExpertise() { return expertise; }
    public boolean isAvailable() { return available; }

    // polymorphism: overrides Person's getProfile
    @Override
    public String getProfile() {
        return super.getProfile() + " | Role: Tour Guide | Expertise: " + expertise;
    }
}
