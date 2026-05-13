import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributes (UML Diagram ke mutabiq)
    private String loginID; // Note: Diagram me loginID/bookingID dono likha lag raha hai
    private String booking; // UML: booking: Booking
    private double amount;
    private String method; // UML: method: payMethod
    private String status;
    private LocalDate date; // UML: date: localDate

    // Static ArrayList: Tamam transactions record karne ke liye
    private static ArrayList<Payment> paymentHistory = new ArrayList<>();
    private static final String FILE_NAME = "payments.txt";

    // Constructor
    public Payment(String loginID, String booking, double amount, String method) {
        this.loginID = loginID;
        this.booking = booking;
        this.amount = amount;
        this.method = method;
        this.status = "Pending";
        this.date = LocalDate.now();
    }

    // Getters aur Setters
    public String getLoginID() {
        return loginID;
    }

    public String getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    // UML Methods & File Handling Implementation
    public void cash() {
        this.method = "Cash";
        this.status = "Completed";
        processPayment();
    }

    public void card() {
        this.method = "Card";
        this.status = "Completed";
        processPayment();
    }

    public void online() {
        this.method = "Online";
        this.status = "Completed";
        processPayment();
    }

    public void getReciept() {
        System.out.println("\n======= TRANSACTION RECEIPT =======");
        System.out.println("Payment ID: " + loginID);
        System.out.println("Booking Reference: " + booking);
        System.out.println("Amount Paid: $" + amount);
        System.out.println("Payment Method: " + method);
        System.out.println("Status: " + status);
        System.out.println("Date: " + date);
        System.out.println("===================================");
    }

    private void processPayment() {
        paymentHistory.add(this);
        saveToFile();
        System.out.println("Payment processed successfully via " + method);
    }

    // File Handling: Archive transactions
    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(paymentHistory);
        } catch (IOException e) {
            System.err.println("Error saving payment logs: " + e.getMessage());
        }
    }
}
