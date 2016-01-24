package in.aqel.quickparksdk.Objects;

/**
 * Created by Ahammad on 24/01/16.
 */
public class Parking {
    public Parking(String name, String id, Double lat, Double lon, int totalCars, int totalBikes,
                   int bookingCharge, int parkingCharge, boolean countAvailable, boolean isOpen,
                   boolean isBooking) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.totalCars = totalCars;
        this.totalBikes = totalBikes;
        this.bookingCharge = bookingCharge;
        this.parkingCharge = parkingCharge;
        this.countAvailable = countAvailable;
        this.isOpen = isOpen;
        this.isBooking = isBooking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public int getTotalCars() {
        return totalCars;
    }

    public void setTotalCars(int totalCars) {
        this.totalCars = totalCars;
    }

    public int getTotalBikes() {
        return totalBikes;
    }

    public void setTotalBikes(int totalBikes) {
        this.totalBikes = totalBikes;
    }

    public int getBookingCharge() {
        return bookingCharge;
    }

    public void setBookingCharge(int bookingCharge) {
        this.bookingCharge = bookingCharge;
    }

    public int getParkingCharge() {
        return parkingCharge;
    }

    public void setParkingCharge(int parkingCharge) {
        this.parkingCharge = parkingCharge;
    }

    public boolean isCountAvailable() {
        return countAvailable;
    }

    public void setCountAvailable(boolean countAvailable) {
        this.countAvailable = countAvailable;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isBooking() {
        return isBooking;
    }

    public void setBooking(boolean booking) {
        isBooking = booking;
    }

    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    Double lat, lon;
    int totalCars;
    int totalBikes;
    int currentBikes;
    int currentCars;

    public int getCurrentBikes() {
        return currentBikes;
    }

    public void setCurrentBikes(int currentBikes) {
        this.currentBikes = currentBikes;
    }

    public int getCurrentCars() {
        return currentCars;
    }

    public void setCurrentCars(int currentCars) {
        this.currentCars = currentCars;
    }

    int bookingCharge;
    int parkingCharge;
    boolean countAvailable, isOpen, isBooking;

}
