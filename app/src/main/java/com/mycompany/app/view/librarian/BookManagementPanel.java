package com.mycompany.app.view.librarian;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BookManagementPanel extends JPanel {

    public JTextField txtTitle, txtAuthor; 
    public JButton btnAdd, btnEdit, btnDelete;
    public JTable bookTable;
    public DefaultTableModel tableModel;

    private static final Color MAROON = new Color(142, 1, 0);
    private static final Color YELLOW = new Color(255, 193, 7);
    private static final Color GREEN = new Color(46, 204, 113);
    private static final Color RED = new Color(231, 76, 60);

    public BookManagementPanel() {
        setLayout(new GridLayout(1, 2, 25, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // ===== LEFT SIDE (Book Management) =====
        JPanel leftCard = new JPanel(new BorderLayout(0, 15));
        leftCard.setBackground(Color.WHITE);
        leftCard.setBorder(BorderFactory.createLineBorder(MAROON, 2, true));

        JPanel leftHeader = new JPanel();
        leftHeader.setBackground(MAROON);
        JLabel lblLeftTitle = new JLabel("Book Management", SwingConstants.CENTER);
        lblLeftTitle.setForeground(Color.WHITE);
        lblLeftTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        leftHeader.add(lblLeftTitle);
        leftHeader.setPreferredSize(new Dimension(0, 55));

        // ===== INPUT AREA =====
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 10, 60));

        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        txtTitle = createStyledField();

        JLabel lblAuthor = new JLabel("Author:");
        lblAuthor.setFont(new Font("Arial", Font.BOLD, 14));
        txtAuthor = createStyledField();

        formPanel.add(lblTitle);
        formPanel.add(txtTitle);
        formPanel.add(lblAuthor);
        formPanel.add(txtAuthor);

        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = createYellowButton("ADD");
        btnEdit = createYellowButton("EDIT");
        btnDelete = createYellowButton("DELETE");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        leftCard.add(leftHeader, BorderLayout.NORTH);
        leftCard.add(formPanel, BorderLayout.CENTER);
        leftCard.add(buttonPanel, BorderLayout.SOUTH);

        // ===== RIGHT SIDE (Book Status Table) =====
        JPanel rightCard = new JPanel(new BorderLayout(0, 0));
        rightCard.setBackground(Color.WHITE);
        rightCard.setBorder(BorderFactory.createLineBorder(MAROON, 2, true));

        JPanel rightHeader = new JPanel();
        rightHeader.setBackground(MAROON);
        JLabel lblRightTitle = new JLabel("Book Status", SwingConstants.CENTER);
        lblRightTitle.setForeground(Color.WHITE);
        lblRightTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        rightHeader.add(lblRightTitle);
        rightHeader.setPreferredSize(new Dimension(0, 55));

        // ===== TABLE (Title + Status) =====
        String[] columns = {"Title", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        bookTable.setRowHeight(36);
        bookTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        bookTable.getTableHeader().setBackground(Color.WHITE);
        bookTable.getTableHeader().setForeground(MAROON);
        bookTable.setGridColor(new Color(220, 220, 220));
        bookTable.setShowGrid(true);
        bookTable.setIntercellSpacing(new Dimension(0, 6));

        // ===== STATUS COLORS & ALIGNMENT =====
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String text = value == null ? "" : value.toString().toLowerCase();

                c.setBackground(isSelected ? new Color(255, 239, 170) : Color.WHITE);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                // Column 0 = Title
                if (column == 0) {
                    c.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    c.setForeground(Color.BLACK);
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
                }
                // Column 1 = Status
                else {
                    c.setFont(new Font("Arial", Font.PLAIN, 15));
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                    if (text.contains("available")) c.setForeground(GREEN);
                    else if (text.contains("borrowed")) c.setForeground(RED);
                    else c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        bookTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        bookTable.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);

        // ===== SCROLL PANE =====
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createLineBorder(MAROON, 1, true));

        rightCard.add(rightHeader, BorderLayout.NORTH);
        rightCard.add(scrollPane, BorderLayout.CENTER);

        // ===== ADD BOTH PANELS =====
        add(leftCard);
        add(rightCard);
    }

    // ===== Styled Text Field =====
    private JTextField createStyledField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(220, 22));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAROON, 2, true),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBackground(Color.WHITE);
        return field;
    }

    // ===== Yellow Buttons =====
    private JButton createYellowButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 32));
        button.setBackground(YELLOW);
        button.setForeground(MAROON);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(YELLOW, 2, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 213, 79));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(YELLOW);
            }
        });
        return button;
    }

    // ===== Test Run =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Book Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 600);
            frame.setLocationRelativeTo(null);
            frame.add(new BookManagementPanel());
            frame.setVisible(true);
        });
    }
}
