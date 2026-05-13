package touristPackage;

public class Hotel implements Displayable {

    //ATTRIBUTES
    private String hotelID;
    private String name;
    private String location;
    private int starRating;
    private double pricePerNight;
    private int availableRooms;

    //STATIC VARIABLE
    public static int hotelCount = 0;

    //STATIC FINAL VARIABLE
    public static final double TAX_RATE = 0.15;

    //CONSTRUCTOR
    public Hotel(String hotelID, String name, String location, int starRating, double pricePerNight, int availableRooms) {
        this.hotelID = hotelID;
        this.name = name;
        this.location = location;
        this.starRating = starRating;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
        hotelCount++;
    }

    //GETTERS
    public String getHotelID() {
        return hotelID;
    }   

    public String getName() {
        return name;
    }       

    public String getLocation() {
        return location;
    }

    public int getStarRating() {
        return starRating;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    //SETTERS
    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }   

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStarRating(int starRating) {
        if(starRating >= 1 && starRating <= 5) {
            this.starRating = starRating;
        } else {
            System.out.println("Invalid star rating. Please enter a value between 1 and 5");
        }
    }

    public void setPricePerNight(double pricePerNight) {
        if(pricePerNight > 0) {
            this.pricePerNight = pricePerNight;
        } else {
            System.out.println("Invalid price. Please enter a positive value.");
        }
    }

    public void setAvailableRooms(int availableRooms) {
        if(availableRooms >= 0) {
            this.availableRooms = availableRooms;
        } else {
            System.out.println("Rooms cannot be negative.");
        }
    }

    //ADD 
    public void addHotel() {
        System.out.println("Hotel added: " + name);
    }

    //VIEW
    public void viewHotel() {
        System.out.println("===== HOTEL DETAILS =====");
        System.out.println("Hotel ID: " + getHotelID());
        System.out.println("Name: " + getName());
        System.out.println("Location: " + getLocation());
        System.out.println("Star Rating: " + getStarRating());
        System.out.println("Price Per Night: $" + getPricePerNight());
        System.out.println("Available Rooms: " + getAvailableRooms());
    }

    //UPDATE
    public void updateHotel(String newName, String newLocation, double newPricePerNight) {
        this.name = newName;
        this.location = newLocation;

        setPricePerNight(newPricePerNight);
        System.out.println("Hotel updated successfully: " + getName());
    }

    //DELETE
    public void deleteHotel() {
        System.out.println("Hotel deleted successfully!!");
    }
    
    //CHECK AVAILABILITY
    public boolean checkAvailability(int roomsRequested) {
        return availableRooms >= roomsRequested;
    }

    //TOTAL PRICE CALCULATION
    public double calculateTotalPrice(int nights) {
        double total = pricePerNight * nights;
        total = total + (total * TAX_RATE); 
        return total;
    }

    @Override
    public void displayDetails() {
        viewHotel();
    }
}