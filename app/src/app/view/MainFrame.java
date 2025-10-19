package app.view;

import app.config.AppConfig;
import javax.swing.*;

/**
 * MainFrame.java
 * 👉 This is the main Swing window (GUI).
 * All user interactions happen here (e.g., buttons, labels, forms).
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle(AppConfig.APP_NAME); // pull the window title from shared config
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
    }
}
