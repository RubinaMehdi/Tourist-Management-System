package model;

import interfaces.*;

public abstract class Person implements Displayable {

    // ATTRIBUTES
    protected String personID;
    protected String name;
    protected String email;
    protected String password;

    // STATIC FINAL VARIABLE
    public static final String SYSTEM = "Tourist Management System";

    // CONSTRUCTOR
    public Person(String personID, String name, String email, String password) {
        this.personID = personID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // GETTERS
    public String getPersonID() {
        return personID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // SETTERS
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Name can not be empty.");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            System.out.println("Invalid email format.");
        }
    }

    public void setPassword(String password) {
        if (password.length() >= 6) {
            this.password = password;
        } else {
            System.out.println("Password too short.");
        }
    }

    // LOGIN METHOD
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // LOGOUT METHOD
    public void logout() {
        System.out.println(name + " has logged out.");
    }

    // GET PROFILE METHOD
    public String getProfile() {
        return "ID: " + personID + "\nName: " + name + "\nEmail: " + email;
    }

    // CHANGE PASSWORD METHOD
    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            setPassword(newPassword);
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Old password is incorrect. Password change failed.");
        }
    }

    // ABSTRACT METHOD
    public abstract void showMenu();

    public abstract void displayRole();

    public abstract void displayDetails();
}
