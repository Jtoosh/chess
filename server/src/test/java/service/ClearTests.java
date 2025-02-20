package service;


import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClearTests {
  ClearService clearService=new ClearService();
  GameDAO gameDAO=new MemoryGameDAO();
  AuthDAO authDAO=new MemoryAuthDAO();
  UserDAO userDAO=new MemoryUserDAO();

  //Comparison arrays
  GameData[] gameExpected = {};
  AuthData[] authExpected = {};
  UserData[] userExpected = {};

  @BeforeEach
  public void setup() {

    gameDAO.createGame();
    userDAO.createUser("jtoosh", "jtoosh111", "email.com");
    authDAO.createAuth("jtoosh");
    this.clearService=new ClearService(userDAO, authDAO, gameDAO);
  }

  @Test
  void ClearTest() {
    Assertions.assertNotNull(gameDAO.getGameList());
    Assertions.assertNotNull(userDAO.getUserList());
    Assertions.assertNotNull(authDAO.getAuthDataList());
    clearService.clear();
    Assertions.assertArrayEquals(this.gameExpected, gameDAO.getGameList().toArray(), "GameData list not cleared");
    Assertions.assertArrayEquals(this.userExpected, userDAO.getUserList().toArray(), "UserData list not cleared");
    Assertions.assertArrayEquals(this.authExpected, authDAO.getAuthDataList().toArray(), "AuthData list not cleared");
  }
}
