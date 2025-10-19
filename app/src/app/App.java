package app;

import app.view.MainFrame;

/**
 * App.java
 * 👉 This is the entry point of the application.
 * It launches the MainFrame (main GUI window).
 */
public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> 
            new MainFrame().setVisible(true)
        );
    }
}
