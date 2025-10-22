package com.mycompany.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.mycompany.app.controller.LibrarianDashboardController;
import com.mycompany.app.controller.UserController;
import com.mycompany.app.model.User;
import com.mycompany.app.view.LibrarianDashboardView;
import com.mycompany.app.view.UserView;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::showLauncher);
    }

    private static void showLauncher() {
        // Frame setup
        JFrame frame = new JFrame("Library System Launcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center window
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245)); // light gray background

        // ===== Header Panel =====
        JLabel lblTitle = new JLabel("📚 Library Management System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(128, 0, 0)); // Maroon
        lblTitle.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        // ===== Buttons Panel =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));

        JButton btnUser = createStyledButton("User Dashboard");
        JButton btnLibrarian = createStyledButton("Librarian Dashboard");

        buttonPanel.add(btnUser);
        buttonPanel.add(btnLibrarian);

        // ===== Footer Label =====
        JLabel lblFooter = new JLabel("© 2025 Library System", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(100, 100, 100));
        lblFooter.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // ===== Add to Frame =====
        frame.add(lblTitle, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(lblFooter, BorderLayout.SOUTH);

        // ===== Button Actions =====
        btnUser.addActionListener(e -> {
            frame.dispose();
            openUserDashboard();
        });

        btnLibrarian.addActionListener(e -> {
            frame.dispose();
            openLibrarianDashboard();
        });

        frame.setVisible(true);
    }

    // ===== Helper: Styled Button =====
    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(153, 0, 0)); // dark maroon
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(180, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(153, 0, 0));
            }
        });
        return button;
    }

    // ===== Original Dashboard Methods =====
    private static void openUserDashboard() {
        User model = new User("");
        com.mycompany.app.view.UserView view = new UserView();
        new UserController(model, view);
        view.setVisible(true);
    }

    private static void openLibrarianDashboard() {
        LibrarianDashboardView view = new LibrarianDashboardView();
        new LibrarianDashboardController(view);
        view.setVisible(true);
    }
}
