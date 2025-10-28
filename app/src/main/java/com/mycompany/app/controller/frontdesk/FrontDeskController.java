package com.mycompany.app.controller.frontdesk;

import com.mycompany.app.controller.AIAssistant;
import com.mycompany.app.model.LibraryCatalog;

public class FrontDeskController {
    private final LibraryCatalog catalog;
    private final AIAssistant ai;

    public FrontDeskController(LibraryCatalog catalog) {
        this.catalog = catalog;
        this.ai = new AIAssistant(catalog);
    }

    public String getAIResponse(String message) {
        String normalized = message == null ? "" : message.trim();
        if (normalized.isEmpty()) {
            return "Hello! Ask me about the EVSU Library collection.";
        }
        return ai.respond(normalized);
    }

    public String[][] getBooksData() {
        var books = catalog.getAvailableBooks();
        String[][] data = new String[books.size()][3];
        for (int i = 0; i < books.size(); i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = books.get(i)[1];
            data[i][2] = books.get(i)[2];
        }
        return data;
    }
}