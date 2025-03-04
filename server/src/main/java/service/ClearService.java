package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import request.ClearRequest;
import response.ClearResponse;

public class ClearService extends ParentService{

  public ClearService(){

  }

  public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
    super(userDAO, authDAO, gameDAO);
  }

  public ClearResponse clear() {

   super.clearAuthData();
   super.clearUserData();
   super.clearGameData();
   return new ClearResponse(200, null);
  }
}
