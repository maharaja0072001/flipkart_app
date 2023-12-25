package com.flipkart.dao;

import com.flipkart.custom_exceptions.DatabaseConnectionException;
import com.flipkart.custom_exceptions.DatabaseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <p>
 * Provides connection with the database.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class DBConnection {

    private static Connection connection;

    /**
     * <p>
     * Default constructor of DBConnection class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private DBConnection() {}

    /**
     * <p>
     * Creates a connection with database and returns it.
     * </p>
     *
     * @return {@link Connection} of the interface.
     */
    public static Connection getConnection() throws SQLException {
        if (null == connection) {
            final FileReader fileReader;

            try {
                fileReader = new FileReader("resources/db_credentials.properties");
            } catch (FileNotFoundException e) {
                throw new DatabaseConnectionException("File not found");
            }
            final Properties properties = new Properties();

            try {
                properties.load(fileReader);
            } catch (IOException e) {
                throw new DatabaseException(e.getMessage());
            }
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        }

        return connection;
    }
}
