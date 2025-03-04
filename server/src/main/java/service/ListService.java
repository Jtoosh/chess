package service;

import dataaccess.AuthDAO;
import dataaccess.AuthorizationException;
import dataaccess.GameDAO;
import request.ListRequest;
import response.ListResponse;

public class ListService extends ParentService{

  public ListService (AuthDAO authDAO, GameDAO gameDAO){
    super(null, authDAO, gameDAO);
  }

  public ListResponse listGames(ListRequest request){
    if (getAuthData(request.authToken()) == null){
      throw new AuthorizationException("Error: unauthorized");
    }
    return new ListResponse(super.gameDataAccess.getGameList());
  }
}
