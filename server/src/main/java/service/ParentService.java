package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ParentService {
  private UserDAO userDataAccess = new SQLUserDAO();
  private AuthDAO authDataAccess = new SQLAuthDAO();
  protected GameDAO gameDataAccess = new SQLGameDAO();

  public ParentService(){

  }

  public ParentService(UserDAO userDao, AuthDAO authDao, GameDAO gameDao){
    this.userDataAccess = userDao;
    this.authDataAccess = authDao;
    this.gameDataAccess = gameDao;
  }

  //Getters
  public UserData getUser(String username) {
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

  public AuthData createAuthData (String username){
    return authDataAccess.createAuth(username);
  }

  public int createGame(String whiteUsername, String blackUsername, String gameName){
    return gameDataAccess.createGame(whiteUsername, blackUsername, gameName);
  }

  public void updateGame(int gameID, String teamColorRequest, String username, String game){
    gameDataAccess.updateGame(gameID, teamColorRequest, username, game);
  }


  //Delete methods
  public void deleteAuth(String authToken){
    authDataAccess.deleteAuth(authToken);
  }

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
