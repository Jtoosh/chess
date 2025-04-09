package server;


import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import request.*;
import response.*;
import service.*;
import spark.*;
import websocket.Connection;
import websocket.commands.UserGameCommand;
import websocket.messages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

@WebSocket
public class Server {
    private final UserDAO userDataAccess = new SQLUserDAO();
    private final AuthDAO authDataAccess = new SQLAuthDAO();
    private final GameDAO gameDataAccess = new SQLGameDAO();

    private final Serializer serializer = new Serializer();
    private final ClearService clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);
    private final RegisterService registerService = new RegisterService(userDataAccess, authDataAccess);
    private final LoginService loginService = new LoginService(userDataAccess, authDataAccess);
    private final LogoutService logoutService = new LogoutService(authDataAccess);
    private final ListService listService = new ListService(authDataAccess, gameDataAccess);
    private final CreateService createService = new CreateService(authDataAccess, gameDataAccess);
    private final JoinService joinService = new JoinService(userDataAccess, authDataAccess, gameDataAccess);

  private Map<Integer, Collection<String>> activeClients = new TreeMap<>();

    private void handleResponse(Response res, Record request, ServiceHandler service){
      try{
        Record response = service.executeService(request);
        res.status(200);
        res.body(serializer.toJSON(response));
      } catch (IllegalArgumentException e){
        res.status(400);
        res.body(serializer.toJSON(new ErrorResponse(e.getMessage())));
      } catch (AuthorizationException e) {
        res.status(401);
        res.body(serializer.toJSON(new ErrorResponse(e.getMessage())));
      } catch (AlreadyInUseException e){
        res.status(403);
        res.body(serializer.toJSON(new ErrorResponse(e.getMessage())));
      } catch (Exception e) {
        res.status(500);
        res.body(serializer.toJSON(new ErrorResponse(e.getMessage())));
      }
    }

    public int run(int desiredPort) {
      DatabaseManager.createDatabase();

      Spark.port(desiredPort);

      Spark.staticFiles.location("web");
      // Register your endpoints and handle exceptions here.

      Spark.webSocket("/connect", Server.class);

      Spark.init();
      Spark.awaitInitialization();


      //Clear Endpoint
      Spark.delete("/db", (req, res) ->{
        ClearRequest clearRequest = new ClearRequest(req.headers(), req.body());
        ServiceHandler clearServiceLambda = request -> clearService.clear();

        handleResponse(res, clearRequest, clearServiceLambda );
        return res.body();
      });

      //Register Endpoint
      Spark.post("/user", (req, res) ->{
        RegisterRequest registerRequest = serializer.fromJSON(req.body(), RegisterRequest.class);
        ServiceHandler registerServiceLambda = request -> registerService.register((RegisterRequest) request);
        handleResponse(res, registerRequest, registerServiceLambda);
        return res.body();
      });

      //Login Endpoint
      Spark.post("/session", (req, res) ->{
          LoginRequest loginRequest = serializer.fromJSON(req.body(), LoginRequest.class);
          ServiceHandler loginServiceLambda = request -> loginService.login((LoginRequest)request);
          handleResponse(res, loginRequest, loginServiceLambda);
          return res.body();
      });

      //Logout endpoint
      Spark.delete("/session", (req, res) ->{
       LogoutRequest logoutRequest = new LogoutRequest(req.headers("Authorization"));
       ServiceHandler logoutServiceLambda = request -> logoutService.logout((LogoutRequest) request);
       handleResponse(res, logoutRequest, logoutServiceLambda);
       return res.body();
      });

      //List Games endpoint
      Spark.get("/game", (req, res) ->{
          ListRequest listRequest = new ListRequest(req.headers("Authorization"));
          ServiceHandler listServiceLambda = request -> listService.listGames((ListRequest) request);
          handleResponse(res, listRequest, listServiceLambda);
        return res.body();
      });

      //Create game endpoint
      Spark.post("/game", (req, res) -> {
        CreateRequest testRequest = serializer.fromJSON(req.body(), CreateRequest.class);
        CreateRequest createRequest = new CreateRequest(req.headers("Authorization"), testRequest.gameName());
        ServiceHandler createServiceLambda = request -> createService.createGame((CreateRequest) request);
        handleResponse(res, createRequest, createServiceLambda);
        return res.body();
      });

      //Join game endpoint
      Spark.put("/game", (req,res)->{
        JoinRequest tempRequest = serializer.fromJSON(req.body(), JoinRequest.class);
        JoinRequest joinRequest = new JoinRequest(req.headers("Authorization"), tempRequest.playerColor(), tempRequest.gameID());
        ServiceHandler joinServiceLambda = request -> joinService.joinGame((JoinRequest) request);
        handleResponse(res, joinRequest, joinServiceLambda);
        return res.body();
      });

      //TODO: Add /ws endpoint

      return Spark.port();
    }

        public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {

    UserGameCommand parsedMessage = serializer.fromJSON(message, UserGameCommand.class);
    AuthData rootUserAuth = authDataAccess.getAuthData(parsedMessage.getAuthToken());
    GameData currentGameData = gameDataAccess.getGameData(parsedMessage.getGameID());

    Connection connection = new Connection(rootUserAuth.username(), session);

    ArrayList<String> gameClients=new ArrayList<>();
    if (activeClients.containsKey(parsedMessage.getGameID())){
      gameClients =(ArrayList<String>) activeClients.get(parsedMessage.getGameID());
    }
    gameClients.add(rootUserAuth.username());
    activeClients.put(parsedMessage.getGameID(), gameClients);

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
