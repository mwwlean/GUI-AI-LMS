package com.mycompany.app.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {

    // ✅ Fetch list of books (for the table)
    public List<String[]> getAvailableBooks() {
        List<String[]> books = new ArrayList<>();
        String query = "SELECT id, title, author FROM books";

        try (Connection conn = AppConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("title"),
                        rs.getString("author")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // ✅ Check if a book is available
    public boolean checkBookAvailability(String title) {
        String query = "SELECT available FROM books WHERE title = ?";

        try (Connection conn = AppConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("available");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
