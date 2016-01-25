package in.aqel.quickparksdk.Objects;

/**
 * Created by Ahammad on 25/01/16.
 */
public class Booking {
    String parking, user;
    Long time;

    public Booking() {
    }

    public Booking(String parking, String user, Long time) {
        this.parking = parking;
        this.user = user;
        this.time = time;
    }

    public String getParking() {

        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
