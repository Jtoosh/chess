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
  void loginPositiveTest(){

    LoginRequest request = new LoginRequest("jtoosh", "jtoosh111");
    LoginResponse response = loginService.login(request);

    Assertions.assertEquals(200, response.statusCode(),  "Wrong status code, unsuccessful response");
    Assertions.assertEquals(userExpected.username(), response.username(), "Wrong username.");
    Assertions.assertEquals(authExpected.authToken(), response.authToken(), "Wrong authToken");
    Assertions.assertNull(response.errMsg(), "Error message not null");
  }

  @Test
  @DisplayName("Negative: Wrong password")
  void loginTestWrongPassWord() {
    LoginRequest request = new LoginRequest("jtoosh", "jtoosh222");
    LoginResponse response = loginService.login(request);

    Assertions.assertEquals(401, response.statusCode(), "Wrong status code, expected: 401");
    Assertions.assertEquals(userExpected.username(), response.username(), "Wrong username");
    Assertions.assertNotEquals(userExpected.password(), request.password(), "Passwords equivalent, should not match");
    Assertions.assertEquals("Error: unauthorized", response.errMsg(), "Wrong error message");
  }

  @Test
  @DisplayName("Negative: Unknown Username")
  void loginTestNonexistantUser() {
    LoginRequest request = new LoginRequest("jteuscher", "jtoosh111");
    LoginResponse response = loginService.login(request);

    Assertions.assertEquals(500, response.statusCode(), "Wrong status code, expected: 500");
    Assertions.assertNotEquals(userExpected.username(), response.username(), "Username is in database");
    Assertions.assertEquals(userExpected.password(), request.password());
    Assertions.assertEquals("Error: user not found", response.errMsg(), "Wrong error message");
  }

}
