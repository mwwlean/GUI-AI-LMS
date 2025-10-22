package com.mycompany.app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppConfig {

    // ✅ Database connection settings
    private static final String URL = "jdbc:mysql://localhost:3306/evsu_library";
    private static final String USER = "root";  // XAMPP default user
    private static final String PASSWORD = "";  // leave empty if no MySQL password

    // ✅ Get connection (used by models)
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
