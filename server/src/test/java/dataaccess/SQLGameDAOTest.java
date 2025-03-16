package dataaccess;

import chess.ChessGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
i

import server.Serializer;

public class SQLGameDAOTest {
    private final GameDAO gameDataAccess = new SQLGameDAO();
    private final Serializer serializer = new Serializer();

    @BeforeAll
    static void createDatabase(){
        DatabaseManager.createDatabase();
    }

    @BeforeEach
    void setup(){
        try(Connection conn = DatabaseManager.getConnection(); ){
            TestUtilities.setUp(conn);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @AfterEach
    void cleanUp() {
        try (Connection conn = DatabaseManager.getConnection()) {
            TestUtilities.cleanUp(conn);
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }
}
