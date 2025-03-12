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
    static void createDatabase() throws DataAccessException {
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
            var statement1 = "CREATE TABLE users (" +
                    "username varchar not null primary key," +
                    " password varchar not null," +
                    " email varchar not null) " +
                    "IF NOT EXISTS";
            var statement2 = "CREATE TABLE authData (" +
                    "authToken varchar not null, primary key," +
                    "username varchar not null," +
                    "foreign key(username) references users(username))";
            var statement3 = "CREATE TABLE games (" +
                    "id int auto_increment not null primary key," +
                    "gameName varchar not null," +
                    "whiteUsername varchar," +
                    "blackUsername varchar,"+
                    "game object," +
                    "foreign key(whiteUsername) references users(username)," +
                    "foreign key(blackUsername) references users(username)";
            String[] statementArray = {statement1, statement2, statement3};
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
