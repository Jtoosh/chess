package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import response.LoginResponse;

public class LoginTests {
  AuthData authExpected;
  private final AuthDAO authDAO = new MemoryAuthDAO();
  final UserData userExpected = new UserData("jtoosh","jtoosh111", "email.com");
  LoginService loginService;

  @BeforeEach
  void setup(){
    UserDAO userDAO = new MemoryUserDAO();
    userDAO.createUser(userExpected.username(), userExpected.password(), userExpected.email());


    authDAO.createAuth(userExpected.username());
    authExpected = authDAO.getAuthData(userExpected.username());
    loginService = new LoginService(userDAO, authDAO);
  }

  @Test
  @DisplayName("Positive")
  void loginPositiveTest() throws DataAccessException {

    LoginRequest request = new LoginRequest("jtoosh", "jtoosh111");
    LoginResponse response = loginService.login(request);
    authExpected = authDAO.getAuthData(response.username());
    Assertions.assertEquals(userExpected.username(), response.username(), "Wrong username.");
    Assertions.assertNotNull(response.authToken(), "Null authToken");
  }

  @Test
  @DisplayName("Negative: Wrong password")
  void loginTestWrongPassWord() {
    LoginRequest request = new LoginRequest("jtoosh", "jtoosh222");
    Assertions.assertThrows(AuthorizationException.class, () -> loginService.login(request));
  }

  @Test
  @DisplayName("Negative: Unknown Username")
  void loginTestNonexistentUser() {
    LoginRequest request = new LoginRequest("jteuscher", "jtoosh111");
    Assertions.assertThrows(AuthorizationException.class, ()->loginService.login(request));
  }
}
