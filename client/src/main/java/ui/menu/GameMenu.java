package ui.menu;

import chess.ChessMove;
import chess.ChessPosition;
import client.ServerFacade;
import model.GameData;
import ui.Chessboard;
import ui.EscapeSequences;

import java.io.IOException;
import java.util.ArrayList;

public class GameMenu extends ParentMenu {
    private static boolean isObserver = false;
    private static boolean gameIsOver = false;

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
                serverFacade.sendUserGameCommand("LEAVE", gameData.gameID(), null);
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + String.format(MenuStrings.POSTLOGIN_MENU, username));
                yield "postlogin";
            }
            //TODO: Add support for pawn promotion
            case "move" ->{
                if (isObserver){
                    System.out.println(EscapeSequences.RESET_TEXT_COLOR + "Sorry, observers can't make moves.");
                    yield "game";
                } else if (gameIsOver) {
                    System.out.println(EscapeSequences.RESET_TEXT_COLOR + "Sorry, this game has ended, and no more moves can be made");
                    yield "game";
                }
                validateInput(parts, 2);
                //get square from input
                ChessMove desiredMove = generateMove(parts[1]);
                serverFacade.sendUserGameCommand("MAKE_MOVE", gameData.gameID(), desiredMove);
                yield "game";
            }
            case "resign" -> {
                validateInput(parts, 1);
                //TODO: Tests want an ErrorMsg for Double Resign
                serverFacade.sendUserGameCommand("RESIGN", gameData.gameID(), null);
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

    private static ChessMove generateMove(String moveDescription){
        String[] indexes = moveDescription.split("(?<=[a-h][1-8])");
        int[] startIndexes = Chessboard.findSquareIndexes(indexes[0]);
        int[] endIndexes = Chessboard.findSquareIndexes(indexes[1]);
        ChessPosition startPos = new ChessPosition(startIndexes[0]+1, startIndexes[1]+1);
        ChessPosition endPos = new ChessPosition(endIndexes[0]+1, endIndexes[1]+1);
        return new ChessMove(startPos, endPos, null);
    }


    public static void setIsObserver(boolean flag){isObserver = flag;}

    public static void setGameIsOver(boolean flag){gameIsOver = flag;}

}
