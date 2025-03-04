package service;


import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import response.ClearResponse;

class ClearTests {
  ClearService clearService=new ClearService();
  final GameDAO gameDAO=new MemoryGameDAO();
  final AuthDAO authDAO=new MemoryAuthDAO();
  final UserDAO userDAO=new MemoryUserDAO();

  //Comparison arrays
  final GameData[] gameExpected = {};
  final AuthData[] authExpected = {};
  final UserData[] userExpected = {};

  @BeforeEach
  public void setup() {

    gameDAO.createGame("jtoosh", "puggy", "jtoosh vs. puggy");
    userDAO.createUser("jtoosh", "jtoosh111", "email.com");
    authDAO.createAuth("jtoosh");
    this.clearService=new ClearService(userDAO, authDAO, gameDAO);
  }

  @Test
  @DisplayName("Positive Test")
  void ClearPositiveTest() {
    Assertions.assertNotNull(gameDAO.getGameList());
    Assertions.assertNotNull(userDAO.getUserList());
    Assertions.assertNotNull(authDAO.getAuthDataList());
    clearService.clear();
    Assertions.assertArrayEquals(this.gameExpected, gameDAO.getGameList().toArray(), "GameData list not cleared");
    Assertions.assertArrayEquals(this.userExpected, userDAO.getUserList().toArray(), "UserData list not cleared");
    Assertions.assertArrayEquals(this.authExpected, authDAO.getAuthDataList().toArray(), "AuthData list not cleared");
  }


  @Test
  @DisplayName("Negative: Already cleared")
  @Disabled
  void ClearNegativeTest(){
    clearService.clear();
    // Check that clear doesn't execute when db is already empty.
    ClearResponse response = clearService.clear();
    Assertions.assertEquals(500, response.statusCode());
    Assertions.assertEquals("Error: DB already empty", response.errMsg());
  }
}
