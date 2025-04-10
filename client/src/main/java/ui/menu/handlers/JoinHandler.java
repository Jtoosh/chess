package ui.menu.handlers;

import client.*;
import model.GameData;
import ui.Chessboard;
import ui.EscapeSequences;
import ui.menu.*;
import websocket.commands.UserGameCommand;

import static ui.menu.ParentMenu.*;

import java.io.IOException;
import java.util.ArrayList;

public class JoinHandler {
  private static GameData gameData;

  public static String joinHandle (String[] parts, ServerFacade serverFacade){
    if (!validateInput(parts, 3)){
      return "postlogin";
    }
    int id = Integer.parseInt(parts[1]);
    String teamColor = parts[2];
    String gameName;
    try {
      ArrayList<GameData> gamesList = (ArrayList<GameData>) serverFacade.listGames();

      serverFacade.joinGame(id, teamColor);

      gamesList = (ArrayList<GameData>) serverFacade.listGames();
      gameData = findGame(gamesList, id);
      gameName = gameData.gameName();

    } catch (AuthorizationException e){
      System.out.println(ErrorStrings.LOGOUT_UNAUTH);
      return "postlogin";
    } catch (IllegalArgumentException e){
      System.out.println(ErrorStrings.JOIN_BAD_REQUEST);
      return "postlogin";
    } catch (AlreadyInUseException e){
      System.out.println(ErrorStrings.JOIN_TEAM_COLOR_TAKEN);
      return "postlogin";
    } catch (IOException e) {
      System.out.println(ErrorStrings.IO_EXCEPTION);
      return "postlogin";
    }
    //TODO: Send CONNECT UserGameCommand to server
    serverFacade.sendUserGameCommand("CONNECT", gameData.gameID(), null);

    //Draw chess board now handled in WS notifier

    System.out.print(EscapeSequences.RESET_BG_COLOR);
    System.out.println(EscapeSequences.RESET_TEXT_COLOR + String.format(MenuStrings.GAMEPLAY_MENU, gameName));
    return "game";
  }

  public static GameData getGameData(){
    return gameData;
  }
}
