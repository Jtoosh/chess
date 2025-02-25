package service;

import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.util.log.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;

public class LoginTests {
  private static final Logger log=LoggerFactory.getLogger(LoginTests.class);
  AuthData authExpected;
  UserData userExpected = new UserData("jtoosh","jtoosh111", "email.com");
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
  @DisplayName("Login Service, Positive")
  void LoginPositiveTest(){

    LoginRequest request = new LoginRequest("jtoosh", "jtoosh111");
    LoginResponse response = loginService.login(request);

    Assertions.assertEquals(200, response.statusCode(),  "Wrong status code, unsuccessful response");
    Assertions.assertEquals(userExpected.username(), response.username(), "Wrong username.");
    Assertions.assertEquals(authExpected.authToken(), response.authToken(), "Wrong authToken");
    Assertions.assertNull(response.errMsg(), "Error message not null");
  }
}
