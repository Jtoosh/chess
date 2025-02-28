package service;

import dataaccess.GameDAO;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.ListRequest;
import response.ListResponse;

import java.util.UUID;

public class ListTests {
    private final GameDAO gameDAO = new MemoryGameDAO();
    private final ListService testListService = new ListService();
  AuthData authDataForReq = new AuthData(UUID.randomUUID().toString(), "jtoosh");

  @Test
  @DisplayName("Positive")
  void listGamesTest(){
    ListRequest testRequest = new ListRequest(authDataForReq.authToken());
    ListResponse testResponse = testListService.listGames(testRequest);


  }
}
