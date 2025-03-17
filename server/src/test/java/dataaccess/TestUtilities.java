package dataaccess;

import chess.ChessGame;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import server.Serializer;

public class TestUtilities {
    private final static Serializer serializer = new Serializer();

    public static void setUp(Connection conn) throws SQLException {
        var setUpStatement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        var preppedStmt = conn.prepareStatement(setUpStatement);
        preppedStmt.setString(1, "logan");
        preppedStmt.setString(2, "password");
        preppedStmt.setString(3, "email.com");
        preppedStmt.executeUpdate();

        var setUpStatement1 = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        var preppedStmt1 = conn.prepareStatement(setUpStatement1);
        preppedStmt.setString(1, "logdog");
        preppedStmt.setString(2, "better password");
        preppedStmt.setString(3, "newEmail.com");
        preppedStmt.executeUpdate();


        var setUpStatement2 = "INSERT INTO authData (username, authToken) VALUES (?, ?)";
        var preppedStmt2 = conn.prepareStatement(setUpStatement2);
        preppedStmt2.setString(1, "logan");
        preppedStmt2.setString(2, UUID.randomUUID().toString());
        preppedStmt2.executeUpdate();

        var setUpStatement3 = "INSERT INTO games (id, gameName, whiteUsername, blackUsername, ChessGame)" +
                " VALUES (?,?,?,?,? )";
        var preppedStmt3 = conn.prepareStatement(setUpStatement3);
        preppedStmt3.setInt(1, 1000000000);
        preppedStmt3.setString(2, "test Game");
        preppedStmt3.setString(3, "logan");
        preppedStmt3.setString(4, "logdog");
        preppedStmt3.setString(5, serializer.toJSON(new ChessGame()));
        preppedStmt3.executeUpdate();

        var setUpStatement4 = "INSERT INTO games (id, gameName, whiteUsername, blackUsername, ChessGame)" +
                " VALUES (?,?,?,?,? )";
        var preppedStmt4 = conn.prepareStatement(setUpStatement4);
        preppedStmt4.setInt(1, 2000000000);
        preppedStmt4.setString(2, "test Game");
        preppedStmt4.setString(3, "logan");
        preppedStmt4.setString(4, null);
        preppedStmt4.setString(5, serializer.toJSON(new ChessGame()));
        preppedStmt4.executeUpdate();
    }

    public static void cleanUp(Connection conn) throws SQLException{

            String[] usersMade = {"logan", "logdog"};
            for (String username : usersMade) {
                var cleanUpStatement = "DELETE FROM users WHERE username = ?";
                var preppedCleanUp = conn.prepareStatement(cleanUpStatement);
                preppedCleanUp.setString(1, username);
                preppedCleanUp.executeUpdate();

                var cleanUpStatement2 = "DELETE FROM authData WHERE username = ?";
                var preppedCleanUp2 = conn.prepareStatement(cleanUpStatement2);
                preppedCleanUp2.setString(1, username);
                preppedCleanUp2.executeUpdate();

                var cleanUpStatement3 = "DELETE FROM games WHERE id = ?";
                var preppedCleanUp3 = conn.prepareStatement(cleanUpStatement3);
                preppedCleanUp3.setInt(1,1);
                preppedCleanUp3.executeUpdate();
            }
    }
}
