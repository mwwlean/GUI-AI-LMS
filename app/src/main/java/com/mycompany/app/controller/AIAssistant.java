package com.mycompany.app.controller;

import com.mycompany.app.model.LibraryCatalog;

public class AIAssistant {

    private final LibraryCatalog libraryCatalog;

    public AIAssistant(LibraryCatalog libraryCatalog) {
        this.libraryCatalog = libraryCatalog;
    }

    public String respond(String userInput) {
        userInput = userInput.trim().toLowerCase();

        if (userInput.startsWith("borrow") || userInput.startsWith("return")) {
            String title = extractBookTitle(userInput);
            if (title == null || title.isEmpty()) {
                return "Please type the book title after the command. Example: Borrow Clean Code";
            }

            boolean available = libraryCatalog.checkBookAvailability(title);
            if (available) {
                return "✅ The book \"" + title + "\" is available.\nPlease enter your Student ID and Name to continue the request.";
            } else {
                return "❌ Sorry, the book \"" + title + "\" is currently unavailable.";
            }

        } else if (userInput.matches(".*\\d+.*")) {
            return "📘 Thank you! Your request will now be reviewed by the librarian.\nPlease wait for confirmation.";
        }

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
