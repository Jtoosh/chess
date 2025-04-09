package ui.menu.handlers;

import client.AuthorizationException;
import client.ServerFacade;
import model.GameData;
import ui.Chessboard;
import ui.EscapeSequences;
import ui.menu.*;

import java.io.IOException;
import java.util.ArrayList;

import static ui.menu.ParentMenu.validateInput;
import static ui.menu.PostloginMenu.findGame;

public class ObserveHandler {

  private static GameData gameData;

  public static String observeHandle (String[] parts, ServerFacade serverFacade){

    if (!validateInput(parts, 2)){
      return "postlogin";
    }
    int observeId = Integer.parseInt(parts[1]);
    try {
      ArrayList<GameData> gamesList = (ArrayList<GameData>) serverFacade.listGames();
      gameData= findGame(gamesList, observeId);

      //Print Chessboard using returned list
    } catch (AuthorizationException e){
      System.out.println(ErrorStrings.LOGOUT_UNAUTH);
      return "postlogin";
    } catch (IllegalArgumentException e){
      System.out.println(ErrorStrings.JOIN_BAD_REQUEST);
      return "postlogin";
    }catch (IOException e) {
      System.out.println(ErrorStrings.IO_EXCEPTION);
      return "postlogin";
    }
    serverFacade.sendUserGameCommand("CONNECT", gameData.gameID());
    //Draw chessboard

    Chessboard.draw("light", gameData.game().getBoard(), null);
    System.out.print(EscapeSequences.RESET_BG_COLOR);
    System.out.println(EscapeSequences.RESET_TEXT_COLOR + String.format(MenuStrings.GAMEPLAY_MENU, gameData.gameName()));
    return "game";
  }

  public static GameData getGameData(){
    return gameData;
  }
}
