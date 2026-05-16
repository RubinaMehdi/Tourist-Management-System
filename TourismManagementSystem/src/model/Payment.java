package model;

import interfaces.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Payment implements Payable, Serializable {
    private static final long serialVersionUID = 1L;

    private String loginID;
    private String booking;
    private double amount;
    private String method;
    private String status;
    private LocalDate date;

    private static ArrayList<Payment> paymentHistory = new ArrayList<>();
    private static final String FILE_NAME = "payments.txt";

    public Payment(String loginID, String booking, double amount, String method) {
        this.loginID = loginID;
        this.booking = booking;
        this.amount = amount;
        this.method = method;
        this.status = "Pending";
        this.date = LocalDate.now();
    }

    // Getters
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

    // Payable interface methods
    @Override
    public boolean pay(double amount) {
        this.amount = amount;
        this.status = "Completed";
        processPayment();
        return true;
    }

    @Override
    public void getReceipt() {
        System.out.println("======= RECEIPT =======");
        System.out.println("Payment ID : " + loginID);
        System.out.println("Booking    : " + booking);
        System.out.println("Amount     : $" + amount);
        System.out.println("Method     : " + method);
        System.out.println("Status     : " + status);
        System.out.println("Date       : " + date);
        System.out.println("=======================");
    }

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

    private void processPayment() {
        paymentHistory.add(this);
        saveToFile();
    }

    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(paymentHistory);
        } catch (IOException e) {
            System.err.println("Error saving payment: " + e.getMessage());
        }
    }
}