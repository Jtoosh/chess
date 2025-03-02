package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import request.ListRequest;
import response.ListResponse;

public class ListService extends ParentService{

  public ListService (AuthDAO authDAO, GameDAO gameDAO){
    super(null, authDAO, gameDAO);
  }

  public ListResponse listGames(ListRequest request){
    if (getAuthData(request.authToken()) == null){
      return new ListResponse(401, null, "Error: unauthorized");
    } else if (super.gameDataAccess.getGameList().isEmpty()){
      return new ListResponse(500, null, "Error: no games stored");
    }
    return new ListResponse(200,super.gameDataAccess.getGameList(), null);
  }
}
