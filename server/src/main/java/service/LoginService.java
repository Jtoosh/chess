package service;

import dataaccess.AuthDAO;
import dataaccess.AuthorizationException;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import response.LoginResponse;

public class LoginService extends ParentService{

  public LoginService(UserDAO userDAO, AuthDAO authDAO){
    super(userDAO, authDAO, null);
  }

  public LoginResponse login (LoginRequest request) {
    UserData user = getUser(request.username());
    if (user == null){
      throw new AuthorizationException("Error: unauthorized");
    }
    else if (!user.password().equals(request.password())){
      throw new AuthorizationException("Error: unauthorized");
    }
    else{
      AuthData userAuth = createAuthData(user.username());
      return new LoginResponse(user.username(), userAuth.authToken());
    }
  }
}
