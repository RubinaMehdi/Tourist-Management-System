import java.util.ArrayList;

// Main class to run and test everything
// Shows: inheritance, polymorphism, aggregation, composition,
//        upcasting, downcasting, file handling, exception handling, generics
public class Main {

    public static void main(String[] args) {

        System.out.println("=== TOUR MANAGEMENT SYSTEM ===\n");

        // --- 1. INHERITANCE + CONSTRUCTOR CHAINING ---
        // TourGuide extends Person (inheritance)
        // super() is called inside TourGuide constructor (constructor chaining)
        TourGuide guide1 = new TourGuide("P001", "Ali Khan", "ali@tour.com", "pass123",
                                          "G001", "English", "Historical Sites");

        TourGuide guide2 = new TourGuide("P002", "Sara Malik", "sara@tour.com", "pass456",
                                          "G002", "French", "Wildlife");

        // --- 2. COMPOSITION (Dining lives inside TourPackage) ---
        // When we make a package with diningIncluded=true, Dining is created inside it
        TourPackage pkg1 = new TourPackage("PKG001", "Lahore Heritage Tour",
                                            "Explore Lahore's history", 1500.0, 5, true);

        TourPackage pkg2 = new TourPackage("PKG002", "Northern Safari",
                                            "Wildlife trip up north", 2200.0, 7, false);

        // add a custom dining option to pkg2 (now it has one too)
        pkg2.setDining("Mountain Kitchen", "Pakistani", "$$");

        // --- 3. AGGREGATION (Hotel, Destination, Guide exist on their own) ---
        Hotel hotel1 = new Hotel("H001", "Pearl Continental", "Lahore", 5, 200.0, 50);
        Destination dest1 = new Destination("D001", "Lahore", "Pakistan", "City of gardens");

        // package uses the hotel and destination but doesn't own them
        pkg1.setHotel(hotel1);
        pkg1.setDestination(dest1);

        // guide is assigned to package (aggregation both ways)
        pkg1.setGuide(guide1); // this also calls guide1.assignPackage(pkg1) inside

        // --- 4. ASSOCIATION (Tourist knows about packages, guides know about tourists) ---
        Tourist tourist1 = new Tourist("P003", "Zain Ahmed", "zain@gmail.com", "t123",
                                        "Pakistani", "PA1234567", 2);

        guide1.addTourist(tourist1); // guide keeps track of their tourists

        // --- 5. ARRAYLIST ---
        ArrayList<TourPackage> allPackages = new ArrayList<>();
        allPackages.add(pkg1);
        allPackages.add(pkg2);

        ArrayList<TourGuide> allGuides = new ArrayList<>();
        allGuides.add(guide1);
        allGuides.add(guide2);

        // --- 6. POLYMORPHISM ---
        // Manageable is the interface - view() works differently for each class
        // (interface is someone else's task, but we call it here)
        System.out.println("--- All Packages ---");
        for (TourPackage p : allPackages) {
            p.view(); // polymorphic call through Manageable
        }

        System.out.println("\n--- All Guides ---");
        for (TourGuide g : allGuides) {
            g.view();
        }

        // --- 7. UPCASTING + DOWNCASTING ---
        // Upcast: TourGuide treated as a Person
        Person p = guide1; // upcasting (implicit)
        System.out.println("\nUpcast - Profile: " + p.getProfile()); // calls overridden version

        // Downcast: back to TourGuide to use guide-specific method
        if (p instanceof TourGuide) {
            TourGuide g = (TourGuide) p; // downcasting (explicit)
            g.viewAssignedTours();
        }

        // --- 8. POLYMORPHISM via abstract method ---
        // Person.getProfile() is overridden in TourGuide (different for each subclass)
        System.out.println("\n--- Profiles ---");
        ArrayList<Person> people = new ArrayList<>();
        people.add(guide1);
        people.add(guide2);
        people.add(tourist1);

        for (Person person : people) {
            System.out.println(person.getProfile()); // each gives a different result
        }

        // --- 9. EXCEPTION HANDLING ---
        System.out.println("\n--- Exception Handling ---");
        try {
            Dining d = pkg1.getDining();
            d.addMenuItem(""); // this should throw an exception
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            // simulate a bad package add
            TourPackage badPkg = new TourPackage(null, null, -100, -1);
            allPackages.add(badPkg);
        } catch (Exception e) {
            System.out.println("Caught bad package: " + e.getMessage());
        }

        // --- 10. DINING MENU ---
        System.out.println("\n--- Dining Details ---");
        Dining dining1 = pkg1.getDining();
        dining1.addMenuItem("Chicken Karahi");
        dining1.addMenuItem("Biryani");
        dining1.addDailySpecial("Nihari (special today)");
        dining1.getMenu();
        dining1.showDailySpecials();

        // --- 11. RATINGS ---
        System.out.println("\n--- Ratings ---");
        ArrayList<Rating> ratings = new ArrayList<>();
        ratings.add(new Rating("R001", tourist1, guide1, 5, "Excellent guide!"));
        ratings.add(new Rating("R002", tourist1, guide1, 4, "Very knowledgeable"));
        guide1.viewRating(ratings);

        // --- 12. FILE HANDLING ---
        System.out.println("\n--- File Handling ---");
        guide1.saveToFile("guides.dat");
        guide2.saveToFile("guides.dat");

        pkg1.saveToFile("packages.dat");

        dining1.saveToFile("dining.dat");

        // load back and print
        System.out.println("\nLoaded guides from file:");
        ArrayList<TourGuide> loadedGuides = TourGuide.loadFromFile("guides.dat");
        for (TourGuide g : loadedGuides) {
            System.out.println("  " + g.getDetails());
        }

        // --- 13. GUIDE AVAILABILITY ---
        System.out.println("\n--- Guide Availability ---");
        guide1.updateAvailability(false); // guide is now busy
        guide2.updateAvailability(true);

        guide1.viewTourists();
        guide2.viewTourists();

        System.out.println("\n=== DONE ===");
    }
}
