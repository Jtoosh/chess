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
import websocket.messages.LoadGame;
import websocket.messages.Notification;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;

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

    Connection connection = new Connection(rootUserAuth.username(), session);

    String userRole;
    if (currentGameData.whiteUsername().equals(rootUserAuth.username())){
      userRole = "the White player";
    }
    else userRole = currentGameData.blackUsername().equals(rootUserAuth.username()) ? "the Black player" : "an observer";
    String responseMessage = "";
    ServerMessage listenerResponse = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
    switch (parsedMessage.getCommandType()){
      case CONNECT:
        ServerMessage rootClientResponse = new LoadGame(currentGameData);
        listenerResponse = new Notification(rootUserAuth.username() + " has connected to the game as " + userRole);
        break;
      case LEAVE:
        listenerResponse = new Notification(rootUserAuth.username() + " has left the game.");
        break;
      case RESIGN:
        listenerResponse = new Notification(rootUserAuth.username() + " has resigned, the game is over.");
    }
    responseMessage = serializer.toJSON(listenerResponse);
    session.getRemote().sendString(responseMessage);

  }
}
