package com.mycompany.app;

import javax.swing.SwingUtilities;

import com.mycompany.app.controller.frontdesk.FrontDeskController;
import com.mycompany.app.controller.librarian.LibrarianDashboardController;
import com.mycompany.app.model.LibraryCatalog;
import com.mycompany.app.view.frontdesk.FrontDeskView;
import com.mycompany.app.view.librarian.LibrarianDashboardView;

public class App {

    private static final boolean RUN_FRONT_DESK = true;
    private static final boolean RUN_LIBRARIAN_DASHBOARD = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            if (RUN_FRONT_DESK) {
                launchFrontDesk();
            }
            if (RUN_LIBRARIAN_DASHBOARD) {
                launchLibrarianDashboard();
            }
        });
    }

    private static void launchFrontDesk() {
        LibraryCatalog catalog = new LibraryCatalog();
        FrontDeskController controller = new FrontDeskController(catalog);
        new FrontDeskView(controller).setVisible(true);
    }

    private static void launchLibrarianDashboard() {
        LibrarianDashboardView view = new LibrarianDashboardView();
        new LibrarianDashboardController(view);
        view.setVisible(true);
    }
}
