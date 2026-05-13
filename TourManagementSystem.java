import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import touristPackage.*;

public class TourManagementSystem {
    
    //GLOBAL SCANNER
    static Scanner sc = new Scanner(System.in);
    
    //GENERIC ARRAYLIST
    static ArrayList<Tourist> tourists = new ArrayList<>();
    static ArrayList<Hotel> hotels = new ArrayList<>();
    static ArrayList<Transport> transports = new ArrayList<>();
    
    //MAIN METHOD
    public static void main(String[] args) {
        
        //SAMPLE DATA
        hotels.add(new Hotel("H001", "Grand Palace Hotel", "New York", 5, 200.0, 50));
        transports.add(new Transport("T001", "Bus", 40, "New York", "Washington D.C."));
        Tourist tourist1 = new Tourist("TR101", "ALi", "ali@gmail.com", "password123", "Pakistani", "P1234567", "123-456-7890", 2);
        tourists.add(tourist1);

        touristMenu(tourist1);
    }

    public static void touristMenu(Tourist tourist) {    
        System.out.print("Do you want to access the Tour Management System as a Tourist (yes/no)? ");
        String userChoice = sc.next();
            if (!userChoice.equalsIgnoreCase("yes")) {
            System.out.println("Tourist access cancelled.");
            return;
            }
        
        int option = -1;
        do {
            System.out.println("*******************************************");
            System.out.println("*             TOURIST MENU                *");
            System.out.println("*-----------------------------------------*");
            System.out.println("*                                         *");
            System.out.println("* Select the option you'd like to explore *");
            System.out.println("*      (Options range from 1 to 9)        *");
            System.out.println("*                                         *");
            System.out.println("*-----------------------------------------*");
            System.out.println("*   1. Add Preferences                    *");
            System.out.println("*   2. View Preferences                   *");
            System.out.println("*   3. Update Preferences                 *");
            System.out.println("*   4. Delete Preferences                 *");
            System.out.println("*   5. View Packages                      *");
            System.out.println("*   6. Book Tours                         *");
            System.out.println("*   7. View Bookings History              *");
            System.out.println("*   8. Make Payments                      *");
            System.out.println("*   9. Manage Destinations                *");
            System.out.println("*   10. See Tour Guides                   *");
            System.out.println("*   11. Give Ratings                      *");
            System.out.println("*   12. View Hotels                       *");
            System.out.println("*   13. View Transports                   *");
            System.out.println("*   14. View Profile                      *");
            System.out.println("*   15. Logout/Back to Login Menu         *");  
            System.out.println("*                                         *");
            System.out.println("*******************************************");
            
            System.out.println("Enter your choice: ");
        
            try {
                option = sc.nextInt();
                sc.nextLine(); 
                
                switch (option) {
                case 1:
                    System.out.println("Enter your preference: ");
                    String pref = sc.nextLine();
                    tourist.addPreference(pref); 
                    break;
                case 2:
                    tourist.viewPreferences();
                    break;
                case 3:
                    System.out.println("Enter the index of the preference to update: ");
                    int index = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the new preference: ");
                    String newPref = sc.nextLine();
                    tourist.updatePreference(index, newPref);
                    break;
                case 4:
                    System.out.println("Enter the preference to delete: ");
                    String prefToDelete = sc.nextLine();
                    tourist.deletePreference(prefToDelete);
                    break;
                case 5:
                    tourist.viewPackages(tourist.getPreferences()); 
                    break;
                case 6:
                    System.out.println("Enter the tour package to book: ");
                    String tourPackage = sc.nextLine();
                    tourist.bookTour(tourPackage);
                    //tourist.bookTour("Hunza Tour");
                    break;
                case 7:
                    System.out.println("Enter Package Name: ");
                    String packageName = sc.nextLine();
                    tourist.bookTour(packageName); 
                    ArrayList<String> bookings = new ArrayList<>();
                    bookings.add("Hunza Tour");
                    bookings.add("Skardu Tour");
                    tourist.viewBookings(bookings);
                    break;
                case 8:
                    tourist.makePayment(100000.0); 
                    break;
                case 9:
                    System.out.println("Enter the destination to manage: ");
                    String destination = sc.nextLine();
                    tourist.manageDestination(destination); 
                    break;
                case 10:
                    tourist.tourGuide("John Doe"); 
                    break;
                case 11:
                    System.out.println("Enter your rating (1-5): ");
                    int stars = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter your feedback: ");
                    String feedback = sc.nextLine();
                    tourist.giveRating(stars, feedback); 
                    break;
                case 12:
                    for(Hotel h : hotels) {
                        h.displayDetails();
                        System.out.println("-------------------");
                    }
                    break;
                case 13:
                    for(Transport t : transports) {
                        t.displayDetails();
                        System.out.println("-------------------");
                    }
                    break;
                case 14:
                    System.out.println("==== TOURIST PROFILE ====");
                    tourist.getProfile();
                    break;
                case 15:
                    tourist.logout();
		            System.out.println("Returning to login menu..."); 
                    return;  
                default:
                    System.out.println("Invalid option!"); break;
                }

                System.out.print("Do you want to continue the Tourist menu (yes/no)? ");
                String count = sc.nextLine();
                    if (!count.equalsIgnoreCase("yes")) {
                    break;
                    }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // clear the invalid input
            } catch (Exception e) {
                System.out.println("Something went wrong");
                sc.nextLine(); // clear the input buffer
                }
            } while (option != 15);
        }
}