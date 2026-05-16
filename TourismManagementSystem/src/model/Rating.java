package model;

import interfaces.*;
import java.io.*;
import java.util.ArrayList;

public class Rating implements Serializable, Displayable {
    private static final long serialVersionUID = 1L;

    // Attributes
    private String ratingID;
    private String touristID; // Tourist ke ID se link
    private String tourGuideID; // TourGuide ke ID se link
    private int ratingValue; // 1 to 5
    private String comments;

    // Static ArrayList: Tamam ratings ko memory me manage karne ke liye
    private static ArrayList<Rating> ratingList = new ArrayList<>();
    private static final String FILE_NAME = "ratings.txt";

    // Constructor
    public Rating(String ratingID, String touristID, String tourGuideID, int ratingValue, String comments) {
        this.ratingID = ratingID;
        this.touristID = touristID;
        this.tourGuideID = tourGuideID;
        this.ratingValue = ratingValue;
        this.comments = comments;
    }

    // Getters aur Setters
    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }

    public String getTouristID() {
        return touristID;
    }

    public void setTouristID(String touristID) {
        this.touristID = touristID;
    }

    public String getTourGuideID() {
        return tourGuideID;
    }

    public void setTourGuideID(String tourGuideID) {
        this.tourGuideID = tourGuideID;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
