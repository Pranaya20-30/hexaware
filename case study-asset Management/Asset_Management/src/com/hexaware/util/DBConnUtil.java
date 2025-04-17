package com.hexaware.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {
    private static Connection conn;
    
    // Static block to load the database properties and establish the connection
    static {
        try {
            // Load properties file
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src/db.properties");
            props.load(fis);
            fis.close();

            // Retrieve the properties from the db.properties file
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the initial connection using the properties
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established.");
        } catch (IOException e) {
            System.err.println("Error loading db.properties file.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
        }
    }

    // Method to get the connection, re-establishing it if necessary
    public static Connection getConnection() {
        try {
            // If connection is null or closed, re-establish the connection
            if (conn == null || conn.isClosed()) {
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream("src/db.properties");
                props.load(fis);
                fis.close();

                // Retrieve the properties again
                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                conn = DriverManager.getConnection(url, username, password);
                System.out.println("Connection re-established.");
            }
        } catch (IOException e) {
            System.err.println("Error loading db.properties file.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error getting connection.");
            e.printStackTrace();
        }
        return conn;
    }

    // Method to close the connection explicitly
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection.");
            e.printStackTrace();
        }
    }
}
