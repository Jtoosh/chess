package service;

import dataaccess.AuthDAO;
import dataaccess.AuthorizationException;
import dataaccess.GameDAO;
import request.CreateRequest;
import response.CreateResponse;

public class CreateService extends ParentService{

  public CreateService(AuthDAO authDAO, GameDAO gameDAO){
    super(null, authDAO, gameDAO);
  }

  public CreateResponse createGame(CreateRequest request){
    if (getAuthData(request.authToken()) == null){
      throw new AuthorizationException("Error: unauthorized");
    }
    return new CreateResponse(createGame(null,null, request.gameName()));
  }
}
