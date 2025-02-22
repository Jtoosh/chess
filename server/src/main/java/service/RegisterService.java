package service;

import request.RegisterRequest;
import response.RegisterResponse;

public class RegisterService extends ParentService{

  public RegisterResponse register(RegisterRequest request){
    if (getUser(request.username()) == null) {
      String username =request.username();
      createUser(username, request.password(), request.email());
      createAuthData(username);
      String authToken = getAuthData(username).authToken();
      return new RegisterResponse(username, authToken);
    }
    else{
      String errorMessage = "Username already in use";
      return null;
    }
  }

}
