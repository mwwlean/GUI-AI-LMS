package com.mycompany.app.view.frontdesk;

import com.mycompany.app.controller.frontdesk.FrontDeskController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.Timer;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

public class LibraryDesign extends JPanel {

    private JTable booksTable;
    private JTextField chatInput;
    private JTextArea chatDisplay;
    private JButton borrowButton, returnButton, sendButton;
    private JLabel timeLabel, dateLabel, dayLabel;
    private JPanel aiCenterPanel;
    private JLabel aiIcon, aiText;
    private final FrontDeskController controller;

    public LibraryDesign(FrontDeskController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 245));

        // --- HEADER ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftHeader.setBackground(new Color(245, 245, 245));

        JLabel logo = new JLabel();
        ImageIcon logoIcon = loadIcon("/images/Library_logo.png");
        if (logoIcon != null) {
            logo.setIcon(logoIcon);
        }
        leftHeader.add(logo);

        JLabel title = new JLabel("<html><b style='color:#800000;font-size:20px;'>EVSU OCC</b><br>"
                + "<span style='font-size:12px;color:#333;'>GUI Based AI-Integrated Library Management System</span></html>");
        leftHeader.add(title);

JButton helpBtn = new RoundedButton("Help", 22);
helpBtn.setBackground(new Color(128, 0, 0));
helpBtn.setForeground(Color.WHITE);
helpBtn.setFont(new Font("Poppins", Font.BOLD, 13));
helpBtn.setBorder(new EmptyBorder(8, 20, 8, 20));
helpBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// 👇 ADD THIS LINE HERE
helpBtn.addActionListener(e -> showHelpWindow());

headerPanel.add(leftHeader, BorderLayout.WEST);
headerPanel.add(helpBtn, BorderLayout.EAST);
add(headerPanel, BorderLayout.NORTH);

        // --- MAIN ---
        JPanel bodyPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bodyPanel.setBackground(getBackground());
        bodyPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        bodyPanel.add(createLeftPanel());
        bodyPanel.add(createRightPanel());
        add(bodyPanel, BorderLayout.CENTER);

        startClock();
    }

private JPanel createLeftPanel() {
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new GridBagLayout());
    leftPanel.setBackground(getBackground());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 0, 10, 0); // vertical gap between cards
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;

    // --- BOOK LIST CARD ---
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 0.55; // around 55% height
    JPanel booksCard = new RoundedPanel(22);
    booksCard.setLayout(new BorderLayout());
    booksCard.setBackground(Color.WHITE);
    booksCard.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

    // Red header bar
    JPanel redHeader = new RoundedPanel(0);
    redHeader.setBackground(new Color(128, 0, 0));
    redHeader.setLayout(new BorderLayout());
    redHeader.setBorder(new EmptyBorder(12, 15, 12, 15));

    JLabel booksTitle = new JLabel("List of Available Books");
    booksTitle.setFont(new Font("Poppins", Font.BOLD, 16));
    booksTitle.setForeground(Color.WHITE);
    redHeader.add(booksTitle, BorderLayout.WEST);
    booksCard.add(redHeader, BorderLayout.NORTH);

// Table
String[] columns = {"#", "Title", "Author"};
DefaultTableModel model = new DefaultTableModel(columns, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};
booksTable = new JTable(model);
booksTable.setDefaultEditor(Object.class, null);
booksTable.getTableHeader().setReorderingAllowed(false);
booksTable.setRowHeight(30);
booksTable.setFont(new Font("Poppins", Font.PLAIN, 13));

// ✅ Bold the column headers
JTableHeader header = booksTable.getTableHeader();
header.setFont(new Font("Poppins", Font.BOLD, 14));
header.setBackground(new Color(240, 240, 240));
header.setForeground(new Color(60, 60, 60));

// ✅ Bold all cell contents
DefaultTableCellRenderer boldRenderer = new DefaultTableCellRenderer();
boldRenderer.setFont(new Font("Poppins", Font.BOLD, 13));
boldRenderer.setHorizontalAlignment(SwingConstants.LEFT);

for (int i = 0; i < booksTable.getColumnCount(); i++) {
    booksTable.getColumnModel().getColumn(i).setCellRenderer(boldRenderer);
}

booksTable.setShowHorizontalLines(true);
booksTable.setShowVerticalLines(false);

JScrollPane tableScroll = new JScrollPane(booksTable);
tableScroll.setBorder(new EmptyBorder(10, 10, 10, 10));
booksCard.add(tableScroll, BorderLayout.CENTER);

    leftPanel.add(booksCard, gbc);

    // --- CLOCK CARD ---
    gbc.gridy = 1;
    gbc.weighty = 0.4; // 40% height for clock
    JPanel clockCard = new RoundedPanel(22);
    clockCard.setLayout(new BorderLayout());
    clockCard.setBackground(Color.WHITE);
    clockCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230), 1, true),
            new EmptyBorder(20, 10, 20, 10)
    ));

    dayLabel = new JLabel("Wednesday", SwingConstants.CENTER);
    dayLabel.setFont(new Font("Poppins", Font.PLAIN, 19));

    timeLabel = new JLabel("10:20 AM", SwingConstants.CENTER);
    timeLabel.setFont(new Font("Poppins", Font.BOLD, 88));

    dateLabel = new JLabel("October 20, 2025", SwingConstants.CENTER);
    dateLabel.setFont(new Font("Poppins", Font.PLAIN, 19));

    clockCard.add(dayLabel, BorderLayout.NORTH);
    clockCard.add(timeLabel, BorderLayout.CENTER);
    clockCard.add(dateLabel, BorderLayout.SOUTH);

    leftPanel.add(clockCard, gbc);

    return leftPanel;
}



    private JPanel createRightPanel() {
        JPanel rightPanel = new RoundedPanel(22);
        rightPanel.setLayout(new BorderLayout(15, 15));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // --- CHAT AREA ---
        JPanel chatArea = new JPanel(new BorderLayout());
        chatArea.setBackground(Color.WHITE);

        // AI center block (robot + text)
        aiCenterPanel = new JPanel(new GridBagLayout());
        aiCenterPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 2, 0); // small gap (2px)

// ICON (replaced emoji with image)
aiIcon = new JLabel();
try {
    ImageIcon icon = loadIcon("/images/ai.png");
    if (icon != null) {
        Image scaledIcon = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        aiIcon.setIcon(new ImageIcon(scaledIcon));
    } else {
        aiIcon.setText("🤖");
        aiIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 90));
    }
} catch (Exception e) {
    aiIcon.setText("🤖"); // fallback if image not found
    aiIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 90));
}
aiCenterPanel.add(aiIcon, gbc);

        // TEXT directly below icon (same center line)
        gbc.gridy++;
        aiText = new JLabel("<html><div style='text-align:center;'>"
                + "<span style='color:#800000;font-size:16px;'>Library AI</span><br>"
                + "<b style='font-size:19px;'>What can I do for you!!?</b>"
                + "</div></html>");
        aiText.setFont(new Font("Poppins", Font.PLAIN, 14));
        aiCenterPanel.add(aiText, gbc);

        // CHAT DISPLAY
        chatDisplay = new JTextArea();
        chatDisplay.setEditable(false);
        chatDisplay.setFont(new Font("Poppins", Font.PLAIN, 13));
        chatDisplay.setLineWrap(true);
        chatDisplay.setWrapStyleWord(true);
        chatDisplay.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Scroll container (no border)
        JScrollPane chatScroll = new JScrollPane(chatDisplay);
        chatScroll.setBorder(BorderFactory.createEmptyBorder());

        // Overlay: AI icon + text disappear when user sends message
        JPanel layeredChat = new JPanel();
        layeredChat.setLayout(new OverlayLayout(layeredChat));
        layeredChat.add(aiCenterPanel);
        layeredChat.add(chatScroll);

        chatArea.add(layeredChat, BorderLayout.CENTER);
        rightPanel.add(chatArea, BorderLayout.CENTER);


        // --- INPUT AREA ---
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonsPanel.setBackground(Color.WHITE);

        borrowButton = new RoundedButton("borrow", 22);
        styleSmallButton(borrowButton);
        returnButton = new RoundedButton("return", 22);
        styleSmallButton(returnButton);

        buttonsPanel.add(borrowButton);
        buttonsPanel.add(returnButton);

        chatInput = new JTextField("Ask Anything!");
        chatInput.setForeground(Color.GRAY);
        chatInput.setFont(new Font("Poppins", Font.PLAIN, 13));
        chatInput.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        chatInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (chatInput.getText().equals("Ask Anything!")) {
                    chatInput.setText("");
                    chatInput.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (chatInput.getText().isEmpty()) {
                    chatInput.setText("Ask Anything!");
                    chatInput.setForeground(Color.GRAY);
                }
            }
        });

        sendButton = new RoundedButton("Send", 22);
        sendButton.setBackground(new Color(128, 0, 0));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Poppins", Font.BOLD, 16)); 
        sendButton.setBorder(new EmptyBorder(12, 28, 12, 28)); // bigger
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        sendButton.addActionListener(e -> {
            String userMessage = chatInput.getText().trim();
            if (!userMessage.isEmpty() && !userMessage.equals("Ask Anything!")) {
                aiCenterPanel.setVisible(false);
                chatDisplay.append("You: " + userMessage + "\n");
                chatInput.setText("");
                String reply = controller.getAIResponse(userMessage);
                chatDisplay.append("AI: " + reply + "\n\n");
            }
        });

        inputPanel.add(buttonsPanel, BorderLayout.NORTH);
        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        rightPanel.add(inputPanel, BorderLayout.SOUTH);

        return rightPanel;
    }

    private void styleSmallButton(JButton button) {
        button.setBackground(new Color(230, 230, 230));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Poppins", Font.BOLD, 14)); // bigger & bold
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 25, 10, 25)); // more padding
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void startClock() {
        Timer timer = new Timer(1000, e -> updateClock());
        timer.start();
        updateClock();
    }

    private void updateClock() {
        Date now = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        timeLabel.setText(timeFormat.format(now));
        dateLabel.setText(dateFormat.format(now));
        dayLabel.setText(dayFormat.format(now));
    }

    private void showHelpWindow() {
    JFrame helpFrame = new JFrame("Help Instructions");
    helpFrame.setSize(600, 500);
    helpFrame.setLocationRelativeTo(null);
    helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JTextArea instructions = new JTextArea();
    instructions.setEditable(false);
    instructions.setFont(new Font("Poppins", Font.PLAIN, 14));
    instructions.setLineWrap(true);
    instructions.setWrapStyleWord(true);

    try {
        // ✅ read the text file inside the same 'view' package
        java.nio.file.Path path = java.nio.file.Paths.get("src/main/java/com/mycompany/app/view/help_instructions.txt");
        String content = java.nio.file.Files.readString(path);
        instructions.setText(content);
    } catch (Exception ex) {
        instructions.setText("⚠️ Error loading help instructions.\nPlease check help_instructions.txt file in /view folder.");
    }

    JScrollPane scroll = new JScrollPane(instructions);
    helpFrame.add(scroll);
    helpFrame.setVisible(true);
}

    // Getters
    public JTable getBooksTable() { return booksTable; }
    public JTextField getChatInput() { return chatInput; }
    public JTextArea getChatDisplay() { return chatDisplay; }
    public JButton getBorrowButton() { return borrowButton; }
    public JButton getReturnButton() { return returnButton; }
    public JButton getSendButton() { return sendButton; }

    private ImageIcon loadIcon(String resourcePath) {
        java.net.URL url = getClass().getResource(resourcePath);
        return url != null ? new ImageIcon(url) : null;
    }

    static class RoundedPanel extends JPanel {
        private final int radius;

        RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
        }
    }

    static class RoundedButton extends JButton {
        private final int radius;

        RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }
}
