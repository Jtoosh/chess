package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;


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
        GameData retrievedGame = gameDataAccess.getGameData(1000000000);
        Assertions.assertEquals(1000000000, retrievedGame.gameID());
        Assertions.assertEquals("test Game",retrievedGame.gameName());
        Assertions.assertEquals("logan", retrievedGame.whiteUsername());
        Assertions.assertEquals("logdog", retrievedGame.blackUsername());
    }

    @Test
    @DisplayName("Get Game Negative")
    void getGameNegative(){
        GameData retrievedGame = gameDataAccess.getGameData(-1);
        Assertions.assertNull(retrievedGame);
    }

    @Test
    @DisplayName("Get GameList Positive")
    void getGameListPos(){
        Collection<GameData> retrievedGameList = gameDataAccess.getGameList();
        Assertions.assertEquals(2, retrievedGameList.size());
    }

    @Test
    @DisplayName("Create Game Positive")
    void createGamePos(){
        int createdGameId = gameDataAccess.createGame("logan", "logdog", "Epic battle");
        Assertions.assertEquals(gameDataAccess.getGameList().size(), createdGameId);
    }

    @Test
    @DisplayName("Create Game Negative")
    void createGameNegative(){
        Assertions.assertThrows(DataAccessException.class, ()-> gameDataAccess.createGame("logan", "logdog", null));
    }

    @Test
    @DisplayName("Update Game Positive")
    void updateGamePos(){
        gameDataAccess.updateGame(2000000000, "BLACK", "logdog");
        GameData updatedGame = gameDataAccess.getGameData(2000000000);
        Assertions.assertEquals("logdog", updatedGame.blackUsername());
    }

    @Test
    @DisplayName("Update Game Negative")
    void updateGameNegative(){
        Assertions.assertThrows(DataAccessException.class, () -> gameDataAccess.updateGame(-144, "PURPLE", "rogdog"));
    }


    @Test
    @DisplayName("Clear Games Positive")
    void clearGamesTest(){
        gameDataAccess.clearGameData();
        Assertions.assertNull(gameDataAccess.getGameData(1));
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
