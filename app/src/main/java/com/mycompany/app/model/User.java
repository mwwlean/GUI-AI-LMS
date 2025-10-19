package com.mycompany.app.model;

/**
 * This is the Model part of MVC.
 * It stores the data of our application.
 * In this case, it only stores the user's name.
 */
public class User {
    private String name;

    // Constructor
    public User(String name) {
        this.name = name;
    }

    // Getter - allows other classes to read the name
    public String getName() {
        return name;
    }

    // Setter - allows other classes to change the name
    public void setName(String name) {
        this.name = name;
    }
}
