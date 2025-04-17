package com.hexaware.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {
    private static Connection conn;

    /**
     * Retrieves a database connection, creating a new one if necessary.
     * @return A valid database connection.
     * @throws SQLException If the connection fails or configuration is invalid.
     */
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            Properties props = loadProperties();
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            // Validate properties
            if (url == null || username == null || password == null) {
                throw new SQLException("Missing database configuration in db.properties");
            }

            System.out.println("Connecting to: " + url + " with user: " + username);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established.");
        }
        return conn;
    }

    /**
     * Retrieves a connection string (e.g., JDBC URL) from db.properties based on the provided key.
     * @param key The property key (e.g., "db.url").
     * @return The connection string associated with the key.
     * @throws SQLException If the properties file cannot be loaded or the key is missing.
     */
    public static String getConnectionString(String key) throws SQLException {
        Properties props = loadProperties();
        String value = props.getProperty(key);
        if (value == null) {
            throw new SQLException("Property '" + key + "' not found in db.properties");
        }
        return value;
    }

    /**
     * Loads properties from db.properties file.
     * @return Loaded Properties object.
     * @throws SQLException If the properties file cannot be loaded.
     */
    private static Properties loadProperties() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = DBConnUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException("db.properties not found in classpath");
            }
            props.load(input);
        } catch (IOException e) {
            throw new SQLException("Failed to load db.properties", e);
        }
        return props;
    }

    /**
     * Closes the database connection if open.
     * @throws SQLException If closing the connection fails.
     */
    public static void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Connection closed.");
        }
    }
}