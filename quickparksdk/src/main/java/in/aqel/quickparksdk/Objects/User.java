package in.aqel.quickparksdk.Objects;

/**
 * Created by Ahammad on 24/01/16.
 */
public class User {
    String email;
    String name;
    String role;

    public User() {
    }

    public User(String email, String name, String role, String id, Float balance) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    Float balance;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
