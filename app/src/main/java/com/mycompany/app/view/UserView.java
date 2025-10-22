package com.mycompany.app.view;

import com.mycompany.app.controller.UserController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Student Front Desk GUI (MVC View)
 * Integrates with LibraryDesign.java for UI appearance.
 * - Displays list of available books from MySQL
 * - Borrow and Return buttons (auto-type command)
 * - Chat panel with AI assistant responses
 */
public class UserView extends JFrame {

    private JTable booksTable;
    private JTextField chatInput;
    private JTextArea chatDisplay;
    private UserController controller;
    private LibraryDesign design;

    public UserView(UserController controller) {
        this.controller = controller;

        // Window setup
        setTitle("Student Front Desk - EVSU Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Use LibraryDesign for layout and styling
        design = new LibraryDesign();
        setContentPane(design);

        // Link components from design
        this.booksTable = design.getBooksTable();
        this.chatInput = design.getChatInput();
        this.chatDisplay = design.getChatDisplay();

        // Attach button listeners
        design.getBorrowButton().addActionListener(e -> setCommandInChat("Borrow "));
        design.getReturnButton().addActionListener(e -> setCommandInChat("Return "));
        design.getSendButton().addActionListener(e -> handleSendMessage());

        // Load books from database
        loadBooksFromDatabase();
    }

    /**
     * Loads book data from the database using the controller.
     */
    private void loadBooksFromDatabase() {
        try {
            String[][] data = controller.getBooksData();
            DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
            model.setRowCount(0); // clear old data

            for (String[] row : data) {
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading books from database: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Auto-fill the chat input field with a command.
     */
    private void setCommandInChat(String command) {
        chatInput.setText(command);
        chatInput.requestFocus();
    }

    /**
     * Handles message sending and AI response logic.
     */
    private void handleSendMessage() {
        String text = chatInput.getText().trim();
        if (text.isEmpty()) return;

        // Append user message
        chatDisplay.append("You: " + text + "\n");

        // Get AI response from controller
        String reply = controller.getAIResponse(text);

        // Append AI reply
        chatDisplay.append("AI: " + reply + "\n\n");

        chatInput.setText("");
    }
}
