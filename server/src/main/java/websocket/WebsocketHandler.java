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

    String userRole;
    if (currentGameData.whiteUsername().equals(rootUserAuth.username())){
      userRole = "the White player";
    }
    else userRole = currentGameData.blackUsername().equals(rootUserAuth.username()) ? "the Black player" : "an observer";

    String responseMessage = "";

    switch (parsedMessage.getCommandType()) {
      case CONNECT ->connect(rootUserAuth.username(), session, userRole, currentGameData);
      case RESIGN -> new Notification(rootUserAuth.username() + " has resigned, the game is over.");

    };
  }

  public void connect(String username, Session session,String userRole, GameData game) throws IOException {
    Connection rootClientConnection = new Connection(username, session);
    connections.add(game.gameID(), rootClientConnection);
    //load game message
    ServerMessage rootClientResponse = new LoadGame(game);
    rootClientConnection.send(serializer.toJSON(rootClientResponse));
    ServerMessage broadcastMessage = new Notification(username + " has connected to the game as " + userRole);
    connections.broadcast(username, serializer.toJSON(broadcastMessage), game.gameID());
  }
}
