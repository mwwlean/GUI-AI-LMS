package com.mycompany.app.controller;

import com.mycompany.app.model.User;
import com.mycompany.app.view.UserView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the Controller part of MVC.
 * It connects the View and the Model.
 * It tells the Model what to do when the user interacts with the GUI.
 */
public class UserController {
    private User model;
    private UserView view;

    public UserController(User model, UserView view) {
        this.model = model;
        this.view = view;

        // Add listener to the button in the view
        this.view.addGreetListener(new GreetListener());
    }

    // Inner class to handle button click
    class GreetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.setName(view.getUserName()); // Update the model with user input
            view.setMessage("Hello, " + model.getName() + "!"); // Show message on GUI
        }
    }
}
