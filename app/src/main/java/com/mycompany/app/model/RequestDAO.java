package com.mycompany.app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class RequestDAO {

    // ✅ Fetch all pending borrow and return requests
public static List<Request> getPendingRequests() {
    List<Request> requests = new ArrayList<>();

    String sql = """
        SELECT 
            t.id,
            CONCAT(u.first_name, ' ', u.last_name) AS student_name,
            b.title AS book_title,
            CASE 
                WHEN t.status = 'requested' THEN 'borrow'
                WHEN t.status = 'return_requested' THEN 'return'
                ELSE t.status
            END AS type,
            t.status
        FROM transactions t
        JOIN users u ON t.user_id = u.id
        JOIN books b ON t.book_id = b.id
        WHERE t.status IN ('requested', 'return_requested')
        ORDER BY t.request_date DESC
    """;

    try (Connection conn = AppConfig.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            requests.add(new Request(
                    rs.getInt("id"),
                    rs.getString("student_name"),
                    rs.getString("book_title"),
                    rs.getString("type"),
                    rs.getString("status")
            ));
        }

        System.out.println("📥 Loaded " + requests.size() + " pending borrow/return requests.");

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Database error (getPendingRequests): " + e.getMessage());
    }

    return requests;
}


    // ✅ Update the request status (approve → 'borrowed', deny → 'denied')
    public static void updateRequestStatus(int id, String newStatus) {
        // Translate status for database consistency
        String mappedStatus = switch (newStatus.toLowerCase()) {
            case "approved" -> "borrowed";
            case "denied" -> "denied";
            default -> newStatus;
        };

        String sql = "UPDATE transactions SET status = ? WHERE id = ?";

        try (Connection conn = AppConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mappedStatus);
            ps.setInt(2, id);
            ps.executeUpdate();

            System.out.println("✅ Updated transaction ID " + id + " → " + mappedStatus);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Database update error: " + e.getMessage());
        }
    }

    // ✅ Optional: Add new request manually (for testing)
    public static void addRequest(int userId, int bookId) {
        String sql = "INSERT INTO transactions (user_id, book_id, status, borrow_date) VALUES (?, ?, 'requested', NOW())";

        try (Connection conn = AppConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();

            System.out.println("📩 Added new borrow request (User " + userId + ", Book " + bookId + ")");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Database insert error: " + e.getMessage());
        }
    }
}
