package com.mycompany.app.controller;

import com.mycompany.app.model.LibraryCatalog;

public class FrontDeskController {
    private LibraryCatalog catalog;
    private AIAssistant ai;

    public FrontDeskController(LibraryCatalog catalog) {
        this.catalog = catalog;
        this.ai = new AIAssistant(catalog);
    }

    public String[][] getBooksData() {
        var books = catalog.getAvailableBooks();
        String[][] data = new String[books.size()][3];
        for (int i = 0; i < books.size(); i++) {
            data[i] = books.get(i);
        }
        return data;
    }

    public boolean isBookAvailable(String title) {
        return catalog.checkBookAvailability(title);
    }

    public String getAIResponse(String message) {
        return ai.respond(message);
    }
}