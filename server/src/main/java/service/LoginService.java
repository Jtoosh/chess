package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import response.LoginResponse;

public class LoginService extends ParentService{

  public LoginService(UserDAO userDAO, AuthDAO authDAO){
    super(userDAO, authDAO, null);
  }

  public LoginResponse login (LoginRequest request){
    UserData user = getUser(request.username());
    if (user == null){
      return new LoginResponse(500, null, null, "Error: user not found");
    }
    else if (!user.password().equals(request.password())){
      return new LoginResponse(401, user.username(), null, "Error: unauthorized");
    }
    else{
      AuthData userAuth = getAuthData(user.username());
      return new LoginResponse(200, user.username(), userAuth.authToken(), null);
    }
  }
}
