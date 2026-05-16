package model;

import interfaces.*;
import java.util.ArrayList;

public class Booking implements Displayable {

    private String bookingID;
    private String touristID;
    private String packageID;
    private String hotelID;
    private String guideID;
    private String status;

    private static ArrayList<Booking> bookingList = new ArrayList<>();

    public Booking(String bookingID, String touristID, String packageID, String hotelID, String guideID) {
        this.bookingID = bookingID;
        this.touristID = touristID;
        this.packageID = packageID;
        this.hotelID = hotelID;
        this.guideID = guideID;
        this.status = "Pending";
    }

    // Getters
    public String getBookingID() {
        return bookingID;
    }

    public String getTouristID() {
        return touristID;
    }

    public String getPackageID() {
        return packageID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public String getGuideID() {
        return guideID;
    }

    public String getStatus() {
        return status;
    }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }

    // Static methods
    public static void addBooking(Booking b) {
        bookingList.add(b);
    }

    public static ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    public void displayDetails() {
        System.out.println("Booking ID : " + bookingID);
        System.out.println("Tourist ID : " + touristID);
        System.out.println("Package ID : " + packageID);
        System.out.println("Hotel ID   : " + hotelID);
        System.out.println("Guide ID   : " + guideID);
        System.out.println("Status     : " + status);
    }

    public String toFileString() {
        return bookingID + "," + touristID + "," + packageID + "," + hotelID + "," + guideID + "," + status;
    }
}