package service;

import dataaccess.AuthDAO;

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
      return new LogoutResponse(401, "Error: provided authToken not found");
    }
    else{
      deleteAuth(request.authToken());
      return new LogoutResponse(200, null);
    }
  }
}
