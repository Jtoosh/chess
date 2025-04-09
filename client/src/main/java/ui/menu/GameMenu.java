package ui.menu;

import client.ServerFacade;
import model.GameData;
import ui.Chessboard;
import ui.EscapeSequences;

public class GameMenu extends ParentMenu {

    public static String eval(String input, ServerFacade serverFacade, String username, GameData gameData){
        String[] parts = input.split(" ");

        return switch (parts[0]) {
            case "help" -> {
                validateInput(parts, 1);
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.GAMEPLAY_HELP);
                yield "game";
            }
            case "redraw" ->{
                Chessboard.draw(findStartColor(username, gameData), gameData.game().getBoard(), null);
                yield "game";
            }
            case "leave" -> {
                validateInput(parts, 1);
                // TODO:Send LEAVE UserGameCommand
                serverFacade.sendUserGameCommand("LEAVE", gameData.gameID());
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + String.format(MenuStrings.POSTLOGIN_MENU, username));
                yield "postlogin";
            }
            case "move" ->{
                    validateInput(parts, 2);
                //get square from input
                yield "game";
            }
            case "resign" -> {
                validateInput(parts, 1);
                //TODO: Send RESIGN UserGameCommand
                serverFacade.sendUserGameCommand("RESIGN", gameData.gameID());
                System.out.println("Well, I guess you lost now.");
                yield "game";
            }
            case "highlight" -> {
                validateInput(parts, 2);
                //get square from input
                Chessboard.draw(findStartColor(username, gameData), gameData.game().getBoard(), parts[1] );
                yield "game";
            }
            default -> {
                System.out.println("Sorry, we don't recognize that command. Make sure your command looks like this:\n " +
                        MenuStrings.GAMEPLAY_HELP);
                yield "game";
            }
        };
    }

    private static String findStartColor (String username, GameData currentGame){
        if (username.equals(currentGame.blackUsername())){
            return "dark";
        } else {
            return "light";}
    }

}
