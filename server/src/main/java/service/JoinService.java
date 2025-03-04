package service;

import dataaccess.*;
import request.JoinRequest;
import response.JoinResponse;

public class JoinService extends ParentService{

  public JoinService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
    super(userDAO, authDAO, gameDAO);
  }

  public JoinResponse joinGame(JoinRequest request){
  if (getAuthData(request.authToken()) == null){
    throw new AuthorizationException("Error: unauthorized");
  } else if (getGameData(request.gameID()) == null ||request.playerColor() == null ){
    throw new IllegalArgumentException("Error: bad request");
  }else if (teamColorCollision(request)){
    throw new AlreadyInUseException("Error: already taken");
  }
  updateGame(request.gameID(), request.playerColor(), getAuthData(request.authToken()).username());
  return new JoinResponse(200);
  }

  private boolean teamColorCollision(JoinRequest request){
    if (request.playerColor().equals("WHITE") && getGameData(request.gameID()).whiteUsername() != null){
      return true;
    } else return request.playerColor().equals("BLACK") && getGameData(request.gameID()).blackUsername() != null;
  }
}
