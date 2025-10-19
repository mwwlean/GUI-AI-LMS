package com.mycompany.app;

import com.mycompany.app.model.User;
import com.mycompany.app.view.UserView;
import com.mycompany.app.controller.UserController;

/**
 * This is the main class to start the application.
 */
public class App {
    public static void main(String[] args) {
        // Create the Model
        User model = new User("");

        // Create the View
        UserView view = new UserView();

        // Create the Controller
        UserController controller = new UserController(model, view);
    }
}
