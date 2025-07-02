package com.playpenbank.model;

/**
 * User class representing a user in the Playpen Bank system.
 * It contains common attributes and methods for both employees and regular users as all abstract classes in the world :D
 *
 */
public abstract class User {
    protected int id;
    protected String dni;
    protected String password;
    protected String name;
    protected String lastName;
    protected String userType; // "employee" or "user"

    public User(int id, String dni, String password, String name, String lastName, String userType) {
        this.id = id;
        this.dni = dni;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setdni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
