package com.mycompany.app.controller;

import com.mycompany.app.model.User;

public class UserController {
    private User userModel;
    private AIAssistant ai;

    public UserController(User userModel) {
        this.userModel = userModel;
        this.ai = new AIAssistant(userModel);
    }

    public String[][] getBooksData() {
        var books = userModel.getAvailableBooks();
        String[][] data = new String[books.size()][3];
        for (int i = 0; i < books.size(); i++) {
            data[i] = books.get(i);
        }
        return data;
    }

    public boolean isBookAvailable(String title) {
        return userModel.checkBookAvailability(title);
    }

    // ✅ Delegate AI reply generation
    public String getAIResponse(String message) {
        return ai.respond(message);
    }
}

