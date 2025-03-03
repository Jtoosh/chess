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
  final UserData userExpected = new UserData("jtoosh","jtoosh111", "email.com");
  LoginService loginService;

  @BeforeEach
  void setup(){
    UserDAO userDAO = new MemoryUserDAO();
    userDAO.createUser(userExpected.username(), userExpected.password(), userExpected.email());

    AuthDAO authDAO = new MemoryAuthDAO();
    authDAO.createAuth(userExpected.username());
    authExpected = authDAO.getAuthData(userExpected.username());
    loginService = new LoginService(userDAO, authDAO);
  }

  @Test
  @DisplayName("Positive")
  void loginPositiveTest() throws DataAccessException {

    LoginRequest request = new LoginRequest("jtoosh", "jtoosh111");
    LoginResponse response = loginService.login(request);

    Assertions.assertEquals(userExpected.username(), response.username(), "Wrong username.");
    Assertions.assertEquals(authExpected.authToken(), response.authToken(), "Wrong authToken");
  }

  @Test
  @DisplayName("Negative: Wrong password")
  void loginTestWrongPassWord() {
    LoginRequest request = new LoginRequest("jtoosh", "jtoosh222");
    Assertions.assertThrows(AuthorizationException.class, () -> loginService.login(request));
  }

  @Test
  @DisplayName("Negative: Unknown Username")
  void loginTestNonexistantUser() {
    LoginRequest request = new LoginRequest("jteuscher", "jtoosh111");
    Assertions.assertThrows(DataAccessException.class, ()->loginService.login(request));
  }
}
