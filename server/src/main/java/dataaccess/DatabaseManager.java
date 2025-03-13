package dataaccess;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) {
                    throw new Exception("Unable to load db.properties");
                }
                Properties props = new Properties();
                props.load(propStream);
                DATABASE_NAME = props.getProperty("db.name");
                USER = props.getProperty("db.user");
                PASSWORD = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                CONNECTION_URL = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    public static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
            createTables();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            conn.setCatalog(DATABASE_NAME);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private static void createTables() throws DataAccessException{
        try {
            var statement0 = "USE chess";

            var statement1 = "CREATE TABLE IF NOT EXISTS users (" +
                    "username varchar(255) NOT NULL PRIMARY KEY," +
                    " password varchar(255) NOT NULL," +
                    " email varchar(255) NOT NULL)";
            var statement2 = "CREATE TABLE IF NOT EXISTS authData (" +
                    "authToken varchar(255) NOT NULL PRIMARY KEY," +
                    " username varchar(255) NOT NULL," +
                    " foreign key(username) REFERENCES users(username))";
            var statement3 = "CREATE TABLE IF NOT EXISTS games (" +
                    "id int AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                    "gameName varchar(255) NOT NULL," +
                    "whiteUsername varchar(255)," +
                    "blackUsername varchar(255),"+
                    "ChessGame varchar(255)," +
                    "foreign key(whiteUsername) REFERENCES users(username)," +
                    "foreign key(blackUsername) REFERENCES users(username))";
            String[] statementArray = {statement0, statement1, statement2, statement3};
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            for (String statement : statementArray){
                try (var preparedStatement = conn.prepareStatement(statement)){
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
