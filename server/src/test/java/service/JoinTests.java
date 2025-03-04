package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.JoinRequest;
import response.JoinResponse;


public class JoinTests {
  private final GameDAO gameDAO = new MemoryGameDAO();
  private final AuthDAO authDAO = new MemoryAuthDAO();
  private final UserDAO userDAO = new MemoryUserDAO();
  private final JoinService testJoinService = new JoinService(userDAO, authDAO, gameDAO);
  private AuthData authDataForReq;

  @BeforeEach
  void setup(){
    authDAO.createAuth("jtoosh");
    this.authDataForReq = authDAO.getAuthData("jtoosh");
    gameDAO.createGame(null, "puggy", "jtoosh v. puggy");
  }

  @Test
  @DisplayName("Positive")
  void joinGameTest(){
    JoinRequest testRequest = new JoinRequest(authDataForReq.authToken(), "WHITE", 1);
    JoinResponse testResponse = testJoinService.joinGame(testRequest);
    GameData actualGameData = gameDAO.getGameData(1);

    Assertions.assertEquals(1, actualGameData.gameID());
    Assertions.assertEquals("jtoosh", actualGameData.whiteUsername());
    Assertions.assertEquals("puggy", actualGameData.blackUsername());
    Assertions.assertEquals("jtoosh v. puggy", actualGameData.gameName());
  }

  @Test
  @DisplayName("Negative: Unauthorized")
  void joinGameUnauthorized(){
    JoinRequest testRequest = new JoinRequest("2342..edfsdf sd-sdfsede sdf", "WHITE", 1);
    Assertions.assertThrows(AuthorizationException.class, ()->testJoinService.joinGame(testRequest));
  }

  @Test
  @DisplayName("Negative: Bad Request")
  void joinGameBadRequest(){
    JoinRequest testRequest = new JoinRequest(authDataForReq.authToken(), "BLACK", 43);
    Assertions.assertThrows(IllegalArgumentException.class, ()-> testJoinService.joinGame(testRequest));
  }

  @Test
  @DisplayName("Negative: Team Color Taken")
  void joinGameStealColor(){
    JoinRequest testRequest = new JoinRequest(authDataForReq.authToken(), "BLACK", 1);
    Assertions.assertThrows(AlreadyInUseException.class, ()-> testJoinService.joinGame(testRequest));
  }


}
