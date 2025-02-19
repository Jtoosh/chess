package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ParentService {
  private UserDAO userDataAccess = new MemoryUserDAO();
  private AuthDAO authDataAccess = new MemoryAuthDAO();
  private GameDAO gameDataAccess = new MemoryGameDAO();

  //Getters
  public UserData getUser(String username){
    return userDataAccess.getUserData(username);
  }

  public AuthData getAuthData(String username){
    return authDataAccess.getAuthData(username);
  }

  public GameData getGameData(int gameID){
    return gameDataAccess.getGameData(gameID);
  }

  //Setters/Create methods

  //Clear methods
  public void clearUserData(){
    userDataAccess.clearUserData();
  }

  public void clearAuthData(){
    authDataAccess.clearAuthData();
  }

  public void clearGameData(){
    gameDataAccess.clearGameData();
  }
}
