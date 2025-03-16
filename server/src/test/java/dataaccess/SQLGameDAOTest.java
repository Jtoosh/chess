package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;


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

    @Test
    @DisplayName("Get Game Positive")
    void getGamePos(){
        GameData retrievedGame = gameDataAccess.getGameData(1);
        Assertions.assertEquals(1, retrievedGame.gameID());
        Assertions.assertEquals("test Game",retrievedGame.gameName());
        Assertions.assertEquals("logan", retrievedGame.whiteUsername());
        Assertions.assertEquals("logdog", retrievedGame.blackUsername());
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
