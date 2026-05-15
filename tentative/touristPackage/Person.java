package touristPackage;

import java.io.Serializable;

/**
 * Abstract base class representing any person in the system.
 * Uses inheritance — Tourist and TourGuide extend this.
 * Implements Serializable so subclasses can be persisted to file.
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String personID;
    protected String name;
    protected String email;
    protected String password;
    public static final String SYSTEM = "Tourist Management System";

    public Person(String personID, String name, String email, String password) {
        this.personID = personID;
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getPersonID() { return personID; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }

    // ── Setters with basic validation ────────────────────────────────────────
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@") && email.contains(".")) this.email = email;
    }

    public void setPassword(String password) {
        if (password != null && password.length() >= 6) this.password = password;
    }

    // ── Business methods ─────────────────────────────────────────────────────
    /**
     * Returns true if supplied credentials match this person's stored credentials.
     */
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void logout() {
        System.out.println(name + " has logged out.");
    }

    public String getProfile() {
        return "ID: " + personID + "\nName: " + name + "\nEmail: " + email;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            setPassword(newPassword);
        }
    }

    // ── Abstract methods (polymorphism) ───────────────────────────────────────
    public abstract void showMenu();
    public abstract void displayRole();
    public abstract void displayDetails();
}
