package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ParentService {
  private UserDAO userDataAccess = new MemoryUserDAO();
  private AuthDAO authDataAccess = new MemoryAuthDAO();
  protected GameDAO gameDataAccess = new MemoryGameDAO();

  public ParentService(){

  }

  public ParentService(UserDAO userDao, AuthDAO authDao, GameDAO gameDao){
    this.userDataAccess = userDao;
    this.authDataAccess = authDao;
    this.gameDataAccess = gameDao;
  }

  //Getters
  public UserData getUser(String username){
    return userDataAccess.getUserData(username);
  }

  public AuthData getAuthData(String stringToFind){
    return authDataAccess.getAuthData(stringToFind);
  }

  public GameData getGameData(int gameID){
    return gameDataAccess.getGameData(gameID);
  }

  //Setters/Create methods
  public void createUser(String username, String password, String email){
    userDataAccess.createUser(username, password, email);
  }

  public void createAuthData (String username){
    authDataAccess.createAuth(username);
  }

  public int createGame(){
    gameDataAccess.createGame();
  }


  //Delete methods
  public void deleteAuth(String authToken){
    authDataAccess.deleteAuth(authToken);
  }

  //Clear methods
  public boolean clearUserData(){
    return userDataAccess.clearUserData();
  }

  public boolean clearAuthData(){
    return authDataAccess.clearAuthData();
  }

  public boolean clearGameData(){
    return gameDataAccess.clearGameData();
  }
}
