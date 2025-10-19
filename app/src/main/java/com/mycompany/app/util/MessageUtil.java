package com.mycompany.app.util;

import javax.swing.JOptionPane;

/**
 * Utility class for showing dialogs/messages.
 */
public class MessageUtil {
    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
