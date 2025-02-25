package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import request.RegisterRequest;
import response.RegisterResponse;

public class RegisterService extends ParentService{

  public RegisterService(UserDAO userDAO, AuthDAO authDAO){
    super(userDAO, authDAO, null);
  }

  public RegisterResponse register(RegisterRequest request){
    if (getUser(request.username()) == null) {
      String username =request.username();
      createUser(username, request.password(), request.email());
      createAuthData(username);
      String authToken = getAuthData(username).authToken();
      return new RegisterResponse(200, username, authToken, null);
    }
    else{
      return new RegisterResponse(200, null, null, "Error: already taken");
    }
  }

}
