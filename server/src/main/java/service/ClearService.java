package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import response.ClearResponse;

public class ClearService extends ParentService{

  public ClearService(){

  }

  public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
    super(userDAO, authDAO, gameDAO);
  }

  public ClearResponse clear() {
    if (super.clearUserData() &&
            super.clearAuthData() &&
            super.clearGameData()) {
      return new ClearResponse(200, null);
    } else {
      return new ClearResponse(500, "Error: DB already empty");
    }
  }
}
