package model;

import interfaces.*;

import java.io.*;
import java.util.ArrayList;

public class Transport implements Displayable {

    // ATTRIBUTES
    private String transportID;
    private String transportType;
    private int capacity;
    private String routeFrom;
    private String routeTo;

    // ASSOCIATION WITH DESTINATION
    // private Destination destination;

    // STATIC VARIABLE
    public static int transportCount = 0;

    // STATIC FINAL VARIABLE
    public static final int MAX_CAPACITY = 50;

    // CONSTRUCTOR
    public Transport(String transportID, String transportType, int capacity, String routeFrom, String routeTo) {
        this.transportID = transportID;
        this.transportType = transportType;
        this.capacity = capacity;
        this.routeFrom = routeFrom;
        this.routeTo = routeTo;
        transportCount++;
    }

    // GETTERS
    public String getTransportID() {
        return transportID;
    }

    public String getTransportType() {
        return transportType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getRouteFrom() {
        return routeFrom;
    }

    public String getRouteTo() {
        return routeTo;
    }

    // SETTERS
    public void setTransportID(String transportID) {
        this.transportID = transportID;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public void setCapacity(int capacity) {
        if (capacity <= MAX_CAPACITY) {
            this.capacity = capacity;
        } else {
            System.out.println("Capacity cannot exceed " + MAX_CAPACITY);
        }
    }

    public void setRouteFrom(String routeFrom) {
        this.routeFrom = routeFrom;
    }

    public void setRouteTo(String routeTo) {
        this.routeTo = routeTo;
    }

    /*
     * Public void setDestination(Destination destination) {
     * this.destination = destination;
     * }
     */

    // ADD TRANSPORT
    public void addTransport() {
        System.out.println("Transport added: " + getTransportType());
    }

    // VIEW TRANSPORT
    public void viewTransport() {
        System.out.println("===== TRANSPORT DETAILS =====");
        System.out.println("Transport ID: " + getTransportID());
        System.out.println("Transport Type: " + getTransportType());
        System.out.println("Capacity: " + getCapacity());
        System.out.println("Route From: " + getRouteFrom());
        System.out.println("Route To: " + getRouteTo());
    }

    // UPDATE TRANSPORT
    public void updateTransport(String newType, int newCapacity, String newRouteFrom, String newRouteTo) {
        this.transportType = newType;

        setCapacity(newCapacity);
        System.out.println("Transport updated: " + getTransportType());
    }

    // DELETE TRANSPORT
    public void deleteTransport() {
        System.out.println("Transport deleted: " + transportType);
    }

    // CHECK AVAILABILITY
    public boolean checkAvailability(int requestedSeats) {
        return capacity >= requestedSeats;
    }

    public void displayDetails() {
        viewTransport();
    }
}
