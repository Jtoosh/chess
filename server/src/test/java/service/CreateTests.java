package service;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.CreateRequest;
import response.CreateResponse;

public class CreateTests {
  private final GameDAO gameDAO = new MemoryGameDAO();
  private final AuthDAO authDAO = new MemoryAuthDAO();
  private final CreateService testCreateService = new CreateService(authDAO, gameDAO);
  private AuthData authDataForReq;

  @BeforeEach
  void setup(){
    authDAO.createAuth("jtoosh");
    this.authDataForReq = authDAO.getAuthData("jtoosh");
    gameDAO.createGame();
  }

  @Test
  @DisplayName("Positive")
  void createPositiveTest(){
    CreateRequest testRequest = new CreateRequest(authDataForReq.authToken(), "My cool game");
    CreateResponse testResponse = testCreateService.createGame(testRequest);

    Assertions.assertEquals(1, testResponse.gameID());
  }

  @Test
  @DisplayName("Negative: Unauthorized")
  void createUnauthorizedTest(){
    CreateRequest testRequest = new CreateRequest("My legit auth token 234c-42f", "My cool game");
    Assertions.assertThrows(AuthorizationException.class, ()->testCreateService.createGame(testRequest));
  }
}
