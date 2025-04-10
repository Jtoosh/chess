package websocket;

import chess.*;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.jetbrains.annotations.NotNull;
import server.Serializer;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    UserGameCommand parsedCommand = serializer.fromJSON(message, UserGameCommand.class);
    AuthData rootUserAuth = authDataAccess.getAuthData(parsedCommand.getAuthToken());
    GameData currentGameData = gameDataAccess.getGameData(parsedCommand.getGameID());
    if (currentGameData == null){
      error(rootUserAuth.username(), session, "the gameID passed in is invalid");
      return;
    }
    if (rootUserAuth == null){
      error("", session, "the authToken pass in is invalid.");
      return;
    }

    switch (parsedCommand.getCommandType()) {
      case CONNECT ->connect(rootUserAuth.username(), session, currentGameData);
      case LEAVE -> leave(rootUserAuth.username(), currentGameData);
      case RESIGN -> resign(rootUserAuth.username(), session, currentGameData);
      case MAKE_MOVE -> {
        MakeMoveCommand moveCommand = (MakeMoveCommand) parsedCommand;
        makeMove(rootUserAuth.username(), session, currentGameData,moveCommand.getMoveToMake());
      }
    }
  }

  public void connect(String username, Session session, @org.jetbrains.annotations.NotNull GameData gameData) throws IOException {
    Connection rootClientConnection = new Connection(username, session);
    connections.add(gameData.gameID(), rootClientConnection);
    //load game message
    ServerMessage rootClientResponse = new LoadGame(gameData);
    rootClientConnection.send(serializer.toJSON(rootClientResponse));

    String userRole = determineUserRole(username, gameData);

    ServerMessage broadcastMessage = new Notification(username + " has connected to the game as " + userRole, false);
    connections.broadcast(username, serializer.toJSON(broadcastMessage), gameData.gameID());
  }

  public void leave(String username, @NotNull GameData gameData) throws IOException {
    String colorToUpdate="";
    if (gameData.whiteUsername() != null && gameData.whiteUsername().equals(username)){
        colorToUpdate = "WHITE";
        gameDataAccess.updateGame(gameData.gameID(), colorToUpdate, null, serializer.toJSON(gameData.game()));
      }

    if ( gameData.blackUsername() != null && gameData.blackUsername().equals(username)){
        colorToUpdate = "BLACK";
        gameDataAccess.updateGame(gameData.gameID(), colorToUpdate, null, serializer.toJSON(gameData.game()));
      }

    ServerMessage broadcastMessage = new Notification(username + " has left the game", false);
    connections.broadcast(username, serializer.toJSON(broadcastMessage), gameData.gameID());
    connections.removeConnectionFromGame(gameData.gameID(), connections.getConnection(username, gameData.gameID()));
  }

  public void resign(String username, Session session, GameData gameData) throws IOException {
    if (determineUserRole(username, gameData).equals("an observer")){
      error(username,session, " can't resign as an observer");
    } else if (gameData.game().isGameOver()) {
      error(username, session, "this game is already over, you can't resign");
    } else {
      gameData.game().resign();
      gameDataAccess.updateGame(gameData.gameID(), null, username, serializer.toJSON(gameData.game()));
      ServerMessage broadcastMessage = new Notification(username + " has resigned, the game is over", true);
      connections.broadcast(null, serializer.toJSON(broadcastMessage), gameData.gameID());
    }
  }

  private void makeMove(String username, Session session, GameData gameData, ChessMove move) throws IOException {
    if (move == null){
      error(username, session, "no move was provided");

    } else if (gameData.game().isGameOver()) {
      error(username, session, "the game is over, no more moves can be made");

    } else {
      int rowIndex = move.getStartPosition().getRow()-1;
      int colIndex = move.getStartPosition().getColumn()-1;
      ChessBoard gameBoard = gameData.game().getBoard();
      ChessPiece pieceToMove = gameBoard.getBoardMatrix()[rowIndex][colIndex];
      if (pieceToMove == null){
        error(username, session, "there is not a chess piece there.");
      } else if (!pieceColorMatchesPlayer(username, pieceToMove,gameData )) {
        error(username, session, "that is not one of your pieces");
      } else{
        try {
          gameData.game().makeMove(move);
        } catch (InvalidMoveException e) {
          error(username, session, "that is not a valid move");
          return;
        }
        gameDataAccess.updateGame(gameData.gameID(),null, username, serializer.toJSON(gameData.game()));
        ServerMessage moveMadeLoadGame = new LoadGame(gameData);
        connections.broadcast(null, serializer.toJSON(moveMadeLoadGame), gameData.gameID());
        String teamColor = determineTeamColor(username, gameData);
        String moveString = generateMoveDescription(username, teamColor, move, pieceToMove);
        ServerMessage audienceNotification = new Notification(moveString, gameData.game().isGameOver());
        connections.broadcast(username, serializer.toJSON(audienceNotification) , gameData.gameID());
      }
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

  private boolean pieceColorMatchesPlayer (String username, ChessPiece piece, GameData game){
    String usernameTeamColor="";
    if (username.equals(game.blackUsername())){
      usernameTeamColor = "BLACK";
    } else if (username.equals(game.whiteUsername())) {
      usernameTeamColor = "WHITE";
    }
    if (usernameTeamColor.equals(piece.getTeamColor().toString())){
      return true;
    } else {return false;}
  }

  private String generateMoveDescription(String username, String teamColor, ChessMove move, ChessPiece piece){
    String pieceTypeString = piece.getPieceType().toString().toLowerCase();
//    String squareString = move.getEndPosition().getRow()
    ArrayList<String> fileLabels = new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g", "h"));
    ArrayList<String> fileLabelsinUse = fileLabels;
    if (teamColor.equals("BLACK")){
      fileLabelsinUse =  new ArrayList<>(fileLabels.reversed());
    }
    String fileString = fileLabelsinUse.get(move.getEndPosition().getColumn()-1);
    String rankString =String.valueOf(move.getEndPosition().getRow());
    String squareString = fileString+rankString;
    return username + " moved their " + pieceTypeString + " to " + squareString;
  }

  private String determineTeamColor(String username, GameData gameData){
    if (username.equals(gameData.whiteUsername())){
      return "WHITE";
    } else if (username.equals(gameData.blackUsername())){
      return "BLACK";
    } else{
      return "";
    }
  }
}
