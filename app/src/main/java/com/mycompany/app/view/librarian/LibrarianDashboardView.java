package com.mycompany.app.view.librarian;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Mao ni ang HEADER
 */
public class LibrarianDashboardView extends JFrame {

    public JButton btnRequests, btnBooks, btnLogout;
    public JPanel mainPanel;
    public CardLayout cardLayout;

    public LibrarianDashboardView() {
        // ===== Window Setup =====
        setTitle("EVSU OCC - Librarian Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 20));
        getContentPane().setBackground(Color.WHITE);

        // ===== Header (Logo + Title + Logout) =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Left: Logo + Text
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftPanel.setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("/images/evsu_logo.png");
        Image scaled = logoIcon.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaled));

        JLabel textLabel = new JLabel("<html>"
                + "<span style='font-size:18px; font-weight:700; color:#8E0100;'>EVSU OCC</span><br>"
                + "<span style='font-size:11px; color:#333;'>GUI Based AI-Integrated Library Management System</span>"
                + "</html>");
        textLabel.setHorizontalAlignment(SwingConstants.LEFT);

        leftPanel.add(logoLabel);
        leftPanel.add(textLabel);

        // Right: Logout Button
        JPanel rightPanel = new JPanel(new GridBagLayout()); // centers vertically
        rightPanel.setBackground(Color.WHITE);

        btnLogout = new JButton("Logout");
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(new Color(142, 1, 0));
        btnLogout.setFont(new Font("Arial", Font.BOLD, 15));
        btnLogout.setPreferredSize(new Dimension(100, 40));
        btnLogout.setBorder(BorderFactory.createLineBorder(new Color(142, 1, 0), 2, true));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rightPanel.add(btnLogout);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        // ===== Maroon Rounded Title Bar =====
        RoundedPanel titlePanel = new RoundedPanel(new Color(142, 1, 0), 25);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.setPreferredSize(new Dimension(880, 60));

        JLabel titleLabel = new JLabel("Librarian Dashboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titlePanel.add(titleLabel);

        // ===== Navigation Buttons (Yellow) =====
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        navPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        navPanel.setBackground(Color.WHITE);

        btnRequests = createYellowButton("Pending Request");
        btnBooks = createYellowButton("Manage Books");

        navPanel.add(btnRequests);
        navPanel.add(btnBooks);

        // ===== Main Content Panel =====
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(new JLabel("Welcome, Librarian!", SwingConstants.CENTER), "home");

        // ===== Combine All =====
        JPanel topSection = new JPanel(new BorderLayout(0, 10));
        topSection.setBackground(Color.WHITE);
        topSection.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
        topSection.add(headerPanel, BorderLayout.NORTH);
        topSection.add(titlePanel, BorderLayout.CENTER);
        topSection.add(navPanel, BorderLayout.SOUTH);

        add(topSection, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // 🟡 Subtle Highlight Logic (Option 2)
        JButton[] navButtons = { btnRequests, btnBooks };

        for (JButton btn : navButtons) {
            btn.addActionListener(e -> {
                // Reset to default (bright yellow)
                for (JButton b : navButtons) {
                    b.setBackground(new Color(255, 193, 7)); // bright yellow
                    b.setForeground(new Color(142, 1, 0));   // maroon
                }

                // Subtle active highlight (darker yellow)
                btn.setBackground(new Color(224, 168, 0)); // slightly darker yellow
                btn.setForeground(new Color(90, 0, 0));    // deeper maroon
            });

            // ✨ Hover effect for better UX
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (btn.getBackground().equals(new Color(255, 193, 7))) {
                        btn.setBackground(new Color(255, 205, 50)); // lighter hover
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!btn.getBackground().equals(new Color(224, 168, 0))) {
                        btn.setBackground(new Color(255, 193, 7));
                    }
                }
            });
        }
    }

    // 🔸 Helper: Yellow Buttons
private JButton createYellowButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(220, 45));
    button.setBackground(new Color(255, 193, 7)); // #FFC107
    button.setForeground(new Color(142, 1, 0));   // #8E0100
    button.setFont(new Font("Arial", Font.BOLD, 17));

    // 🔹 Fix: prevent light blue flash when clicking
    button.setFocusPainted(false);
    button.setContentAreaFilled(false); // disable default background painting
    button.setOpaque(true);             // allow custom background color
    button.setBorderPainted(false);     // stop system border flash
    button.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7), 2, true));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    return button;
}


    // 🔴 Custom Rounded Panel (for title bar)
    static class RoundedPanel extends JPanel {
        private final Color bgColor;
        private final int radius;

        public RoundedPanel(Color color, int radius) {
            this.bgColor = color;
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 🧪 Test Run
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibrarianDashboardView view = new LibrarianDashboardView();
            view.setVisible(true);
        });
    }
}
