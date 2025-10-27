package com.mycompany.app.view.frontdesk;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mycompany.app.controller.frontdesk.FrontDeskController;

public class FrontDeskView extends JFrame {

    private final FrontDeskController controller;
    private final LibraryDesign design;

    public FrontDeskView(FrontDeskController controller) {
        this.controller = controller;
        setTitle("Front Desk - EVSU Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        design = new LibraryDesign();
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
        if (text.isEmpty()) {
            return;
        }

        JTextArea chatDisplay = design.getChatDisplay();
        chatDisplay.append("You: " + text + "\n");

        String reply = controller.getAIResponse(text);
        chatDisplay.append("AI: " + reply + "\n\n");
        chatInput.setText("");
    }
}