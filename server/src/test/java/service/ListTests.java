package service;


import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.ListRequest;
import response.ListResponse;

class ListTests {
    private final GameDAO gameDAO = new MemoryGameDAO();
    private final AuthDAO authDAO = new MemoryAuthDAO();
    private final ListService testListService = new ListService(authDAO, gameDAO);
    private AuthData authDataForReq;

    @BeforeEach
    void setup(){
      authDAO.createAuth("jtoosh");
      this.authDataForReq = authDAO.getAuthData("jtoosh");
      gameDAO.createGame("jtoosh", "puggy", "jtoosh vs. puggy");
    }

  @Test
  @DisplayName("Positive")
  void listGamesTest(){
    ListRequest testRequest = new ListRequest(authDataForReq.authToken());
    ListResponse testResponse = testListService.listGames(testRequest);
    Assertions.assertDoesNotThrow(()-> testListService.listGames(testRequest));
    Assertions.assertNotNull(testResponse.games());

  }

  @Test
  @DisplayName("Negative: Not Authorized")
  void listGamesUnauthorizedTest(){
      ListRequest testRequest = new ListRequest("Super legit auth token");
      Assertions.assertThrows(AuthorizationException.class, ()->testListService.listGames(testRequest));
  }


}

