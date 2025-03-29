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

  public static String observeHandle (String[] parts, ServerFacade serverFacade){
    if (!validateInput(parts, 2)){
      return "postlogin";
    }
    int observeId = Integer.parseInt(parts[1]);
    try {
      ArrayList<GameData> gamesList = (ArrayList<GameData>) serverFacade.listGames();
      findGame(gamesList, observeId);
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
    //Draw chessboard

    Chessboard.main("light");
    System.out.print(EscapeSequences.RESET_BG_COLOR);
    System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.GAMEPLAY_MENU);
    return "game";
  }
}
