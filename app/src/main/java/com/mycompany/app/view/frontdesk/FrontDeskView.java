package com.mycompany.app.view.frontdesk;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mycompany.app.controller.frontdesk.FrontDeskController;

public class FrontDeskView extends JFrame {

    private final FrontDeskController controller;
    private final LibraryDesign design;
    private final Color userBubbleColor = new Color(78, 105, 255);
    private final Color aiBubbleColor = new Color(245, 247, 249);
    private final Color userTextColor = Color.WHITE;
    private final Color aiTextColor = new Color(26, 33, 52);
    private final Color userAvatarColor = new Color(78, 105, 255);
    private final Color aiAvatarColor = new Color(210, 216, 229);
    private final int bubbleRadius = 18;

    public FrontDeskView(FrontDeskController controller) {
        this.controller = controller;
        setTitle("Front Desk - EVSU Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        design = new LibraryDesign(controller);
        setContentPane(design);

        design.getBorrowButton().addActionListener(e -> setCommandInChat("Borrow "));
        design.getReturnButton().addActionListener(e -> setCommandInChat("Return "));
        design.getSendButton().addActionListener(e -> handleSendMessage());

        loadBooksFromDatabase();
    }

    private void loadBooksFromDatabase() {
        String[][] data = controller.getBooksData();
        DefaultTableModel model = (DefaultTableModel) design.getBooksTable().getModel();
        model.setRowCount(0);
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void setCommandInChat(String command) {
        JTextField chatInput = design.getChatInput();
        chatInput.setText(command);
        chatInput.requestFocus();
    }

    private void handleSendMessage() {
        JTextField chatInput = design.getChatInput();
        String text = chatInput.getText().trim();
        if (text.isEmpty() || text.equals("Ask Anything!")) {
            return;
        }
        design.hideAiCenterPanel();
        appendBubble("<b>You:</b> " + text, true);
        String replyHtml = controller.getAIResponseHtml(text);
        appendBubble("<b>AI:</b> " + replyHtml, false);
        chatInput.setText("");
    }

    private void appendBubble(String html, boolean isUser) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(5, 18, 5, 18)); // Reduced vertical padding

        Avatar avatar = new Avatar(isUser ? userAvatarColor : aiAvatarColor, isUser ? "U" : "AI");

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.putClientProperty(JTextPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        textPane.setEditable(false);
        textPane.setOpaque(false);
        textPane.setBorder(BorderFactory.createEmptyBorder());
        textPane.setText(wrapHtml(html, isUser ? "#FFFFFF" : "#1A2134"));

        // Calculate proper dimensions for the text pane
        Dimension textSize = calculateTextSize(textPane, html);
        textPane.setPreferredSize(textSize);
        textPane.setMaximumSize(textSize);
        textPane.setMinimumSize(textSize);

        RoundedPanel bubble = new RoundedPanel(bubbleRadius);
        bubble.setLayout(new BorderLayout());
        bubble.setBackground(isUser ? userBubbleColor : aiBubbleColor);
        bubble.setBorder(new EmptyBorder(8, 12, 8, 12)); // Reduced padding
        bubble.add(textPane, BorderLayout.CENTER);
        
        // Set bubble size based on text content
        int bubbleWidth = textSize.width + 24; // 12 left + 12 right padding
        int bubbleHeight = textSize.height + 16; // 8 top + 8 bottom padding
        bubble.setPreferredSize(new Dimension(bubbleWidth, bubbleHeight));
        bubble.setMaximumSize(new Dimension(bubbleWidth, bubbleHeight));
        bubble.setAlignmentX(isUser ? RIGHT_ALIGNMENT : LEFT_ALIGNMENT);

        if (isUser) {
            row.add(Box.createHorizontalGlue());
            row.add(bubble);
            row.add(Box.createRigidArea(new Dimension(12, 1)));
            row.add(avatar);
        } else {
            row.add(avatar);
            row.add(Box.createRigidArea(new Dimension(12, 1)));
            row.add(bubble);
            row.add(Box.createHorizontalGlue());
        }

        design.getChatStream().add(row);
        design.getChatStream().revalidate();
        design.getChatStream().repaint();
        design.scrollToBottom();
    }

    private Dimension calculateTextSize(JTextPane textPane, String html) {
        // Set a reasonable maximum width for text wrapping
        int maxWidth = 700;
        
        // Create a temporary text pane to measure the text
        JTextPane tempPane = new JTextPane();
        tempPane.setContentType("text/html");
        tempPane.setText(wrapHtml(html, "#000000"));
        tempPane.setSize(maxWidth, Integer.MAX_VALUE);
        
        // Get the preferred size after setting the width constraint
        Dimension preferredSize = tempPane.getPreferredSize();
        
        // Ensure minimum dimensions
        int width = Math.max(preferredSize.width, 50);
        int height = Math.max(preferredSize.height, 20);
        
        // Cap the maximum width
        width = Math.min(width, maxWidth);
        
        return new Dimension(width, height);
    }

    private String wrapHtml(String body, String textColorHex) {
        return """
               <html>
                 <body style="margin:0;padding:0;color:%s;font-family:'Poppins',sans-serif;font-size:13px;line-height:1.4;">
                   %s
                 </body>
               </html>
               """.formatted(textColorHex, body);
    }

    private static class RoundedPanel extends JPanel {
        private final int radius;
        RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class Avatar extends JPanel {
        private final Color color;
        private final String label;
        Avatar(Color color, String label) {
            this.color = color;
            this.label = label;
            setOpaque(false);
            setPreferredSize(new Dimension(36, 36));
            setMinimumSize(new Dimension(36, 36));
            setMaximumSize(new Dimension(36, 36));
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int diameter = Math.min(getWidth(), getHeight());
            g2.setColor(color);
            g2.fillOval(0, 0, diameter, diameter);
            g2.setColor(Color.WHITE);
            Font font = getFont().deriveFont(Font.BOLD, 13f);
            g2.setFont(font);
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(label);
            int textHeight = fm.getAscent();
            int x = (diameter - textWidth) / 2;
            int y = (diameter + textHeight) / 2 - 2;
            g2.drawString(label, x, y);
            g2.dispose();
        }
    }
}