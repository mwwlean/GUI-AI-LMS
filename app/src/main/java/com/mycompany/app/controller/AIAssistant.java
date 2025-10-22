package com.mycompany.app.controller;

import com.mycompany.app.model.User;

public class AIAssistant {

    private User userModel;

    public AIAssistant(User userModel) {
        this.userModel = userModel;
    }

    public String respond(String userInput) {
        userInput = userInput.trim().toLowerCase();

        if (userInput.startsWith("borrow") || userInput.startsWith("return")) {
            String title = extractBookTitle(userInput);
            if (title == null || title.isEmpty()) {
                return "Please type the book title after the command. Example: Borrow Clean Code";
            }

            boolean available = userModel.checkBookAvailability(title);
            if (available) {
                return "✅ The book \"" + title + "\" is available.\nPlease enter your Student ID and Name to continue the request.";
            } else {
                return "❌ Sorry, the book \"" + title + "\" is currently unavailable.";
            }

        } else if (userInput.matches(".*\\d+.*")) {
            // Contains numbers, assuming student entered ID
            return "📘 Thank you! Your request will now be reviewed by the librarian.\nPlease wait for confirmation.";
        }

        // Default help message
        return "🤖 Hello! You can type:\n"
                + "- Borrow [Book Title]\n"
                + "- Return [Book Title]\n"
                + "Example: Borrow Clean Code";
    }

    private String extractBookTitle(String message) {
        String[] parts = message.split(" ", 2);
        if (parts.length > 1) {
            return parts[1].trim();
        }
        return null;
    }
}
