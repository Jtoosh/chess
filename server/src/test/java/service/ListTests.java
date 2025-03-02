package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.ListRequest;
import response.ListResponse;

import java.util.UUID;

public class ListTests {
    private final GameDAO gameDAO = new MemoryGameDAO();
    private final AuthDAO authDAO = new MemoryAuthDAO();
    private final ListService testListService = new ListService(authDAO, gameDAO);
    private AuthData authDataForReq;

    @BeforeEach
    void setup(){
      authDAO.createAuth("jtoosh");
      this.authDataForReq = authDAO.getAuthData("jtoosh");
      gameDAO.createGame();
    }

  @Test
  @DisplayName("Positive")
  void listGamesTest(){
    ListRequest testRequest = new ListRequest(authDataForReq.authToken());
    ListResponse testResponse = testListService.listGames(testRequest);
    Assertions.assertEquals(200, testResponse.statusCode());
    Assertions.assertNotNull(testResponse.games());
    Assertions.assertNull(testResponse.errMsg());
  }

  @Test
  @DisplayName("Negative: Not Authorized")
  void listGamesUnauthorizedTest(){
      ListRequest testRequest = new ListRequest("Super legit auth token");
      ListResponse testReponse = testListService.listGames(testRequest);
      Assertions.assertEquals(401, testReponse.statusCode());
      Assertions.assertNull(testReponse.games());
      Assertions.assertEquals("Error: unauthorized", testReponse.errMsg());
  }

  @Test
  @DisplayName("Negative: No Games in List")
  void listGamesNoGames(){
      gameDAO.clearGameData();
      ListRequest testRequest = new ListRequest(authDataForReq.authToken());
      ListResponse testResponse = testListService.listGames(testRequest);
      Assertions.assertEquals(500, testResponse.statusCode());
      Assertions.assertNull(testResponse.games());
      Assertions.assertEquals("Error: no games stored", testResponse.errMsg());
  }
}
