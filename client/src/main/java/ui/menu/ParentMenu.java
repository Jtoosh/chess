package ui.menu;

import model.GameData;
import ui.EscapeSequences;

import java.util.ArrayList;

public class ParentMenu {
  public static boolean validateInput(String[] input, int intendedLen){
    if (input.length != intendedLen){
      System.out.println(ErrorStrings.INVALID_ARG_NUMBER);
      return false;
    } else { return true;}
  }
  public static void printGames (ArrayList<GameData> gamesListArg){
    for (int i = 0; i < gamesListArg.size(); i++){
      GameData game = gamesListArg.get(i);
      String whitePrintName = (game.whiteUsername() != null) ? game.whiteUsername() : "Color available";
      String blackPrintName = (game.blackUsername() != null) ? game.blackUsername() : "Color available";
      System.out.println(EscapeSequences.SET_TEXT_COLOR_GREEN+
              (i+1) + EscapeSequences.RESET_TEXT_COLOR +
              ": " + game.gameName() + "\n   Players: " + "WHITE: " + EscapeSequences.SET_TEXT_COLOR_BLUE +
              whitePrintName + EscapeSequences.RESET_TEXT_COLOR +
              " BLACK: " + EscapeSequences.SET_TEXT_COLOR_BLUE + blackPrintName + EscapeSequences.RESET_TEXT_COLOR);
    }
  }

  public static GameData findGame(ArrayList<GameData> gamesListArg, int gameIDArg){
    for (GameData game : gamesListArg){
      if (game.gameID() == gameIDArg){
        return game;
      }
    }
    return null;
  }
}
