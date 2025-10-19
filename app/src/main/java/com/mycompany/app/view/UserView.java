package com.mycompany.app.view;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * This is the View part of MVC.
 * It shows the user interface (GUI) and buttons.
 */
public class UserView extends JFrame {
    private JTextField nameField = new JTextField(20);
    private JButton greetButton = new JButton("Greet");
    private JLabel messageLabel = new JLabel("Enter your name and click Greet");

    public UserView() {
        // Set title and default close operation
        this.setTitle("Simple MVC Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        JPanel panel = new JPanel();
        panel.add(nameField);
        panel.add(greetButton);
        panel.add(messageLabel);

        this.setContentPane(panel);
        this.pack(); // Adjusts size
        this.setVisible(true); // Shows the window
    }

    // Methods to interact with controller
    public String getUserName() {
        return nameField.getText();
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    // Connects button click with the controller
    public void addGreetListener(ActionListener listener) {
        greetButton.addActionListener(listener);
    }
}
