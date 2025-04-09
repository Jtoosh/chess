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
//    System.out.println("GameData, pre DB Update: " + currentGameData.toString());



    switch (parsedMessage.getCommandType()) {
      case CONNECT ->connect(rootUserAuth.username(), session, currentGameData);
      case LEAVE -> leave(rootUserAuth.username(), session, currentGameData);
      case RESIGN -> new Notification(rootUserAuth.username() + " has resigned, the game is over.");
    }
  }

  public void connect(String username, Session session, GameData game) throws IOException {
    Connection rootClientConnection = new Connection(username, session);
    connections.add(game.gameID(), rootClientConnection);
    //load game message
    ServerMessage rootClientResponse = new LoadGame(game);
    rootClientConnection.send(serializer.toJSON(rootClientResponse));

    String userRole;
    if (game.whiteUsername().equals(username)){
      userRole = "the White player";
    }
    else userRole = game.blackUsername().equals(username) ? "the Black player" : "an observer";

    ServerMessage broadcastMessage = new Notification(username + " has connected to the game as " + userRole);
    connections.broadcast(username, serializer.toJSON(broadcastMessage), game.gameID());
  }
  public void leave(String username, Session session, GameData game) throws IOException {
    String colorToUpdate="";
    if (game.whiteUsername() != null){
      if (game.whiteUsername().equals(username)){
        colorToUpdate = "WHITE";
        gameDataAccess.updateGame(game.gameID(), colorToUpdate, null);
      }
    }
    if ( game.blackUsername() != null) {
      if (game.blackUsername().equals(username)){
        colorToUpdate = "BLACK";
        gameDataAccess.updateGame(game.gameID(), colorToUpdate, null);
      }
    }
//    System.out.println("GameData, postDB update: " + game);

    ServerMessage broadcastMessage = new Notification(username + " has left the game");
    connections.broadcast(null, serializer.toJSON(broadcastMessage), game.gameID());
    connections.removeConnectionFromGame(game.gameID(), connections.getConnection(username, game.gameID()));
  }
}
