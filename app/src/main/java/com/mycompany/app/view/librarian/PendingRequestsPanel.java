package com.mycompany.app.view.librarian;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.mycompany.app.model.Request;
import com.mycompany.app.model.RequestDAO;

/**
 * Styled Pending Requests Panel (EVSU OCC theme) — auto refresh, no manual refresh button
 */
public class PendingRequestsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JTable table;
    private final DefaultTableModel tableModel;
    private List<Request> requests;

    private static final Color MAROON = new Color(139, 0, 0);
    private static final Color GREEN = new Color(46, 204, 113);

    public PendingRequestsPanel() {
        setLayout(new BorderLayout(12, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));

        // ===== Header Bar =====
        RoundedPanel headerPanel = new RoundedPanel(MAROON, 25);
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("📥 Pending Borrow/Return Requests");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);

        // ===== Table Section =====
        String[] columns = {"Name", "Request", "Action"};
        tableModel = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 2; // Only Action column has buttons
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(MAROON);

        // Action buttons
        TableColumn actionColumn = table.getColumnModel().getColumn(2);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        Border lineBorder = BorderFactory.createLineBorder(MAROON, 2, true);
        scrollPane.setBorder(lineBorder);

        // ===== Add Components =====
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Load data automatically when panel opens
        loadRequests();
    }

    // 🔁 Load pending requests from DB
    private void loadRequests() {
        tableModel.setRowCount(0);
        requests = RequestDAO.getPendingRequests();

        if (requests == null || requests.isEmpty()) {
            tableModel.addRow(new Object[]{"✅ No pending requests found", "", ""});
            return;
        }

        for (Request r : requests) {
            tableModel.addRow(new Object[]{
                    r.getStudentName(),
                    "Requested to " + r.getType() + " \"" + r.getBookTitle() + "\"",
                    "Action"
            });
        }
    }

    // ===== Custom Renderer for Action Buttons =====
    static class ButtonRenderer extends JPanel implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        private final JButton approveButton = new JButton("Approve");
        private final JButton denyButton = new JButton("Deny");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
            setBackground(Color.WHITE);
            styleActionButton(approveButton, new Color(46, 204, 113)); // Green
            styleActionButton(denyButton, new Color(139, 0, 0));       // Maroon
        }

        private void styleActionButton(JButton btn, Color bg) {
            btn.setBackground(bg);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            removeAll();

            Object firstCell = table.getValueAt(row, 0);
            if (!"✅ No pending requests found".equals(firstCell)) {
                add(approveButton);
                add(denyButton);
            }

            return this;
        }
    }

    // ===== Editor: Approve / Deny Actions =====
    class ButtonEditor extends DefaultCellEditor {
        private static final long serialVersionUID = 1L;
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        private final JButton approveButton = new JButton("Approve");
        private final JButton denyButton = new JButton("Deny");
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.setBackground(Color.WHITE);
            styleActionButton(approveButton, GREEN);
            styleActionButton(denyButton, MAROON);

            approveButton.addActionListener(e -> approveRequest());
            denyButton.addActionListener(e -> denyRequest());
            panel.add(approveButton);
            panel.add(denyButton);
        }

        private void styleActionButton(JButton btn, Color bg) {
            btn.setBackground(bg);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);
        }

        private void approveRequest() {
            if (selectedRow < requests.size()) {
                Request selected = requests.get(selectedRow);
                RequestDAO.updateRequestStatus(selected.getId(), "approved");
                JOptionPane.showMessageDialog(PendingRequestsPanel.this, "✅ Request approved!");
                loadRequests();
            }
        }

        private void denyRequest() {
            if (selectedRow < requests.size()) {
                Request selected = requests.get(selectedRow);
                RequestDAO.updateRequestStatus(selected.getId(), "denied");
                JOptionPane.showMessageDialog(PendingRequestsPanel.this, "❌ Request denied!");
                loadRequests();
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;

            Object firstCell = table.getValueAt(row, 0);
            if ("✅ No pending requests found".equals(firstCell)) {
                return new JPanel(); // Empty panel if no pending request
            }

            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    // 🔴 Custom Rounded Panel (for header)
    static class RoundedPanel extends JPanel {
        private static final long serialVersionUID = 1L;
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
            JFrame frame = new JFrame("Pending Requests");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 600);
            frame.add(new PendingRequestsPanel());
            frame.setVisible(true);
        });
    }
}
