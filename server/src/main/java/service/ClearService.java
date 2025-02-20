package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService extends ParentService{

  public ClearService(){

  }

  public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
    super(userDAO, authDAO, gameDAO);
  }

  public void clear(){

    super.clearUserData();
    super.clearAuthData();
    super.clearGameData();
  }
}
