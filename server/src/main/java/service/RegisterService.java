package service;

import dataaccess.AlreadyInUseException;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import request.RegisterRequest;
import response.RegisterResponse;

public class RegisterService extends ParentService{

  public RegisterService(UserDAO userDAO, AuthDAO authDAO){
    super(userDAO, authDAO, null);
  }

  public RegisterResponse register(RegisterRequest request) {
    if (getUser(request.username()) == null) {
      String username =request.username();
      createUser(username, request.password(), request.email());
      createAuthData(username);
      String authToken = getAuthData(username).authToken();
      return new RegisterResponse(username, authToken);
    }
    if (request.password() == null) {
      throw new IllegalArgumentException("Error: bad request");
    }
    else{
      throw new AlreadyInUseException("Error: already taken");
    }
  }

}
