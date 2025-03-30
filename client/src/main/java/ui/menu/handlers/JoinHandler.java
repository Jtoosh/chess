package ui.menu.handlers;

import chess.ChessGame;
import client.*;
import model.GameData;
import ui.Chessboard;
import ui.EscapeSequences;
import ui.menu.*;

import static ui.menu.ParentMenu.*;

import java.io.IOException;
import java.util.ArrayList;

public class JoinHandler {

  public static String joinHandle (String[] parts, ServerFacade serverFacade){
    if (!validateInput(parts, 3)){
      return "postlogin";
    }
    int id = Integer.parseInt(parts[1]);
    String teamColor = parts[2];
    String gameName = "";
    try {
      ArrayList<GameData> gamesList = (ArrayList<GameData>) serverFacade.listGames();
      GameData game = findGame(gamesList, id);
      gameName = game.gameName();
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
    ChessGame game = new ChessGame();
    if (teamColor.equals("WHITE")){
      Chessboard.draw("light", game.getBoard().getBoardMatrix());
    } else {
      Chessboard.draw("dark", game.getBoard().getBoardMatrix());
    }

    System.out.print(EscapeSequences.RESET_BG_COLOR);
    System.out.println(EscapeSequences.RESET_TEXT_COLOR + String.format(MenuStrings.GAMEPLAY_MENU, gameName));
    return "game";
  }
}
