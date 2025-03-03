package service;

import dataaccess.AuthDAO;

import dataaccess.AuthorizationException;
import model.AuthData;
import request.LogoutRequest;
import response.LogoutResponse;

public class LogoutService extends ParentService{
  public LogoutService(AuthDAO authDAO){
    super(null, authDAO, null);
  }
  public LogoutResponse logout (LogoutRequest request){
    AuthData userAuth = getAuthData(request.authToken());
    if (userAuth == null){
      throw new AuthorizationException("Error: unauthorized");
    }
    else{
      deleteAuth(request.authToken());
      return new LogoutResponse(null);
    }
  }
}
