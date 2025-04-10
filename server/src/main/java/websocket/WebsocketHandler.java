package websocket;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.Serializer;
import websocket.commands.UserGameCommand;
import websocket.messages.*;

import java.io.IOException;


@WebSocket
public class WebsocketHandler {

  private final ConnectionManager connections = new ConnectionManager();
  private final Serializer serializer;
  private final AuthDAO authDataAccess;
  private final GameDAO gameDataAccess;

  public WebsocketHandler(Serializer serializerArg, AuthDAO authDAO, GameDAO gameDAO){
    this.serializer = serializerArg;
    this.authDataAccess = authDAO;
    this.gameDataAccess = gameDAO;
  }

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {

    UserGameCommand parsedMessage = serializer.fromJSON(message, UserGameCommand.class);
    AuthData rootUserAuth = authDataAccess.getAuthData(parsedMessage.getAuthToken());
    GameData currentGameData = gameDataAccess.getGameData(parsedMessage.getGameID());

    if (currentGameData == null){
      error(rootUserAuth.username(), session, "the gameID passed in is invalid");
      return;
    }
    if (rootUserAuth == null){
      error("", session, "the authToken pass in is invalid.");
      return;
    }

    switch (parsedMessage.getCommandType()) {
      case CONNECT ->connect(rootUserAuth.username(), session, currentGameData);
      case LEAVE -> leave(rootUserAuth.username(), session, currentGameData);
      case RESIGN -> resign(rootUserAuth.username(), session, currentGameData);
    }
  }

  public void connect(String username, Session session, GameData game) throws IOException {
    Connection rootClientConnection = new Connection(username, session);
    connections.add(game.gameID(), rootClientConnection);
    //load game message
    ServerMessage rootClientResponse = new LoadGame(game);
    rootClientConnection.send(serializer.toJSON(rootClientResponse));

    String userRole = determineUserRole(username, game);

    ServerMessage broadcastMessage = new Notification(username + " has connected to the game as " + userRole, false);
    connections.broadcast(username, serializer.toJSON(broadcastMessage), game.gameID());
  }

  public void leave(String username, Session session, GameData game) throws IOException {
    String colorToUpdate="";
    if (game.whiteUsername() != null && game.whiteUsername().equals(username)){
        colorToUpdate = "WHITE";
        gameDataAccess.updateGame(game.gameID(), colorToUpdate, null, serializer.toJSON(game.game()));
      }

    if ( game.blackUsername() != null && game.blackUsername().equals(username)){
        colorToUpdate = "BLACK";
        gameDataAccess.updateGame(game.gameID(), colorToUpdate, null, serializer.toJSON(game.game()));
      }

    ServerMessage broadcastMessage = new Notification(username + " has left the game", false);
    connections.broadcast(username, serializer.toJSON(broadcastMessage), game.gameID());
    connections.removeConnectionFromGame(game.gameID(), connections.getConnection(username, game.gameID()));
  }

  public void resign(String username, Session session, GameData game) throws IOException {
    if (determineUserRole(username, game).equals("an observer")){
      Connection rootClientConn = connections.getConnection(username,game.gameID());
      ErrorMsg errorResponse = new ErrorMsg("error: " + username + " can't resign as an observer");
      rootClientConn.send(serializer.toJSON(errorResponse));
    }else {
      game.game().resign();
      gameDataAccess.updateGame(game.gameID(), null, username, serializer.toJSON(game.game()));
      ServerMessage broadcastMessage = new Notification(username + " has resigned, the game is over", true);
      connections.broadcast(null, serializer.toJSON(broadcastMessage), game.gameID());
    }
  }

  private void error(String username, Session session, String errorString) throws IOException {
    Connection rootClientConn = new Connection(username, session);
    ServerMessage errorMsg = new ErrorMsg("Error: " + errorString);
    rootClientConn.send(serializer.toJSON(errorMsg));
  }

  private String determineUserRole(String username, GameData game){
    String userRole;
    if (game.whiteUsername() != null && game.whiteUsername().equals(username)){
      userRole = "the White player";
    }
    else if (game.blackUsername() != null && game.blackUsername().equals(username)){
      userRole = "the Black player";
    } else {
      userRole = "an observer";
    }
    return userRole;
  }
}
