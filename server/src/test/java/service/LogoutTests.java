package service;

import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.LogoutRequest;
import response.LogoutResponse;

import java.util.UUID;

public class LogoutTests {
  AuthData authExpected;
  private final AuthDAO authDAO = new MemoryAuthDAO();
  LogoutService logoutService;

  @BeforeEach
  void setup(){
    UserDAO userDAO = new MemoryUserDAO();
    userDAO.createUser("jtoosh","jtoosh111", "email.com");

    authDAO.createAuth("jtoosh");
    authExpected = authDAO.getAuthData("jtoosh");
    logoutService = new LogoutService(authDAO);
  }

  @Test
  @DisplayName("Positive")
  void logoutTestPositive(){
    LogoutRequest request = new LogoutRequest(authExpected.authToken());
    LogoutResponse response = logoutService.logout(request);

    Assertions.assertEquals(200, response.statusCode(), "Wrong status code, expected: 200");
    Assertions.assertNull(response.errMsg());
    Assertions.assertNull(authDAO.getAuthData(authExpected.authToken()));
  }

  @Test
  @DisplayName("Negative: authToken not in db")
  void logoutTestAuthNotFound(){
    LogoutRequest request = new LogoutRequest(UUID.randomUUID().toString());
    LogoutResponse response = logoutService.logout(request);

    Assertions.assertEquals(401, response.statusCode());
    Assertions.assertEquals("Error: provided authToken not found", response.errMsg());
    Assertions.assertNotNull(authDAO.getAuthData(authExpected.authToken()));
  }
}
