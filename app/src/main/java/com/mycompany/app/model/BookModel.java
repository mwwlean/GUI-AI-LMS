package com.mycompany.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class BookModel {

    // ✅ Get all books from database
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT title, author, status FROM books";

        try (Connection conn = AppConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("status").equalsIgnoreCase("available")
                );
                books.add(book);
            }

            System.out.println("📚 Loaded " + books.size() + " books from database.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "❌ Database error (getAllBooks): " + e.getMessage());
        }

        return books;
    }

    // ✅ Add a new book (Title + Author + Status)
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, status) VALUES (?, ?, ?)";
        try (Connection conn = AppConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.isAvailable() ? "available" : "borrowed");
            ps.executeUpdate();

            System.out.println("✅ Added book: " + book.getTitle());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "❌ Database error (addBook): " + e.getMessage());
        }
    }

    // ✅ Delete book by title
    public void deleteBook(String title) {
        String sql = "DELETE FROM books WHERE title = ?";
        try (Connection conn = AppConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("🗑️ Deleted book: " + title);
            else
                System.out.println("⚠️ No book found with title: " + title);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "❌ Database error (deleteBook): " + e.getMessage());
        }
    }

    // ✅ Edit book title and author
    public void updateBook(String oldTitle, String newTitle, String newAuthor) {
        String sql = "UPDATE books SET title = ?, author = ? WHERE title = ?";
        try (Connection conn = AppConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newTitle);
            ps.setString(2, newAuthor);
            ps.setString(3, oldTitle);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✏️ Updated book: " + oldTitle + " → " + newTitle + " (" + newAuthor + ")");
            else
                System.out.println("⚠️ No book found with title: " + oldTitle);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "❌ Database error (updateBook): " + e.getMessage());
        }
    }

    // ✅ NEW: Get a specific book by title (used for editing)
    public Book getBookByTitle(String title) {
        String sql = "SELECT title, author, status FROM books WHERE title = ?";
        try (Connection conn = AppConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("status").equalsIgnoreCase("available")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "❌ Database error (getBookByTitle): " + e.getMessage());
        }

        return null;
    }
}
