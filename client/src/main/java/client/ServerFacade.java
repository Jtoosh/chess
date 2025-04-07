package client;


import model.*;
import request.*;
import response.*;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.util.Collection;

public class ServerFacade {
    private String endpointURL = "http://localhost:";
    private final HttpCommunicator clientCommunicator = new HttpCommunicator();
    private WebsocketCommunicator wsCommunitcator;
    private AuthData clientAuthData = new AuthData(null, null);
    private final ClientSerializer serializer = new ClientSerializer();

    public ServerFacade (int port, ServerMessageObserver msgObserver) {
        this.endpointURL = this.endpointURL + port;
        try {
            this.wsCommunitcator = new WebsocketCommunicator(msgObserver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AuthData getClientAuthData() {
        return clientAuthData;
    }

    public AuthData register(String username, String password, String email) throws IOException {
        UserData registerData = new UserData(username, password, email);

        HttpHandler registerHandler = () -> clientCommunicator.httpRequest(
                registerData, null, this.endpointURL + "/user", "POST", AuthData.class);
        AuthData result = (AuthData) handleResponse(registerHandler);
        this.clientAuthData = result;
        return result;
    }

    public AuthData login(String username, String password) throws IOException{
        UserData loginData = new UserData(username, password, null);
        HttpHandler loginHandler = ()-> clientCommunicator.httpRequest(
                loginData, null, this.endpointURL + "/session", "POST", AuthData.class);
        AuthData result = (AuthData) handleResponse(loginHandler);
        this.clientAuthData = result;
        return result;
    }

    public void logout() throws IOException {
        HttpHandler logoutHandler = ()-> clientCommunicator.httpRequest(
                this.clientAuthData, this.clientAuthData.authToken(), endpointURL + "/session", "DELETE", null);
        handleResponse(logoutHandler);
        this.clientAuthData = new AuthData(null,null);

    }

    public int createGame(String gameName) throws IOException {
        CreateRequest clientRequest = new CreateRequest(this.clientAuthData.authToken(), gameName);
        HttpHandler createGameHandler = ()-> clientCommunicator.httpRequest(
                clientRequest,this.clientAuthData.authToken(), endpointURL + "/game", "POST", CreateResponse.class);
        CreateResponse result = (CreateResponse) handleResponse(createGameHandler);
        return result.gameID();
    }

    public Collection<GameData> listGames() throws IOException {
        HttpHandler listGamesHandler = ()-> clientCommunicator.httpRequest(
                null, this.clientAuthData.authToken(), endpointURL + "/game", "GET", ListResponse.class);
        ListResponse result = (ListResponse) handleResponse(listGamesHandler);
        return result.games();
    }

    public void joinGame(int gameID, String requestedColor) throws IOException {
        JoinRequest clientRequest = new JoinRequest(this.clientAuthData.authToken(),requestedColor, gameID );
        HttpHandler joinGameHandler = ()-> clientCommunicator.httpRequest(
                clientRequest, this.clientAuthData.authToken(), endpointURL + "/game", "PUT", null);
        handleResponse(joinGameHandler);
    }

    public void clear() throws IOException{
        clientCommunicator.httpRequest(
                null, null, endpointURL + "/db", "DELETE", null);
    }

    //TODO: Add method for sending UserGameCommands
    public void sendUserGameCommand(String commandType, Integer gameID){
        UserGameCommand.CommandType parsedCommandType = null;
        switch (commandType) {
            case "CONNECT":
                 parsedCommandType=  UserGameCommand.CommandType.CONNECT;

                 break;
            case "MAKE_MOVE":
                parsedCommandType = UserGameCommand.CommandType.MAKE_MOVE;
                break;
            case "LEAVE":
                parsedCommandType = UserGameCommand.CommandType.LEAVE;
                break;
            case "RESIGN":
                parsedCommandType = UserGameCommand.CommandType.RESIGN;
                break;
        }
        UserGameCommand command = new UserGameCommand(parsedCommandType, this.clientAuthData.authToken(), gameID);
      try {
        wsCommunitcator.send(serializer.toJSON(command));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    }

    private Record handleResponse(HttpHandler commHandler) throws IOException {
        try{
            return commHandler.makeRequest();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e);
        } catch (AuthorizationException e){
            throw new AuthorizationException(e.getMessage());
        } catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        } catch (IOException e){
            throw new IOException(e);
        }
    }
}
