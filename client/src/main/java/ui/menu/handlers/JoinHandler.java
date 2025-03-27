package ui.menu.handlers;

import client.*;
import ui.Chessboard;
import ui.EscapeSequences;
import ui.menu.*;

import static ui.menu.ParentMenu.validateInput;

import java.io.IOException;

public class JoinHandler {

  public static String joinHandle (String[] parts, ServerFacade serverFacade){
    if (!validateInput(parts, 3)){
      return "postlogin";
    }
    int id = Integer.parseInt(parts[1]);
    String teamColor = parts[2];
    try {
      serverFacade.joinGame(id, teamColor);
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
    //Draw chess board
    if (teamColor.equals("WHITE")){
      Chessboard.main("light");
    } else {
      Chessboard.main("dark");
    }
    System.out.print(EscapeSequences.RESET_BG_COLOR);
    System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.GAMEPLAY_MENU);
    return "game";
  }
}
