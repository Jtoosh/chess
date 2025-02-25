package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import response.RegisterResponse;

class RegisterTests {
  AuthData[] authExpected = {};
  UserData userExpected = new UserData("jtoosh","jtoosh111", "email.com");

  @Test
  @DisplayName("Register Service, Positive")
  void RegisterTest(){

    RegisterService service = new RegisterService(new MemoryUserDAO(), new MemoryAuthDAO());
    RegisterRequest request = new RegisterRequest(userExpected.username(), userExpected.password(), userExpected.email());
    RegisterResponse response = service.register(request);
    UserData user = service.getUser(response.username());

    Assertions.assertEquals(userExpected.username(), user.username(), "Usernames don't match.");
    Assertions.assertEquals(userExpected.password(), user.password(), "Passwords don't match");

    Assertions.assertEquals(userExpected.email(), user.email(), "Email addresses don't match");

    Assertions.assertEquals(response.authToken(), service.getAuthData(user.username()).authToken(), "AuthTokens don't match");
  }

  @Test
  @DisplayName("Register Service, Negative: UsernameTaken")
  void UsernameTakenTest(){
    //Add initial user
    RegisterService service = new RegisterService(new MemoryUserDAO(), new MemoryAuthDAO());
    RegisterRequest request = new RegisterRequest(userExpected.username(), userExpected.password(), userExpected.email());
    service.register(request);

    RegisterRequest request2 = new RegisterRequest(userExpected.username(), "jtoosh222", "other_email.com");
    RegisterResponse response = service.register(request2);

    Assertions.assertEquals("Error: already taken", response.errMsg(), "Error messages don't match");
  }
}
