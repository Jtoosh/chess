package ui.menu;

import client.AlreadyInUseException;
import client.AuthorizationException;
import client.ServerFacade;
import model.GameData;
import ui.Chessboard;
import ui.EscapeSequences;

import java.io.IOException;
import java.util.ArrayList;

public class PostloginMenu extends ParentMenu{

    public static String eval(String input, ServerFacade serverFacade){
        String[] parts = input.split(" ");
        switch(parts[0]){

            //LOGOUT CASE
            case "logout":
              try {
                serverFacade.logout();
              } catch (AuthorizationException e){
                  System.out.println(ErrorStrings.LOGOUT_UNAUTH);
              } catch (IOException e) {
                System.out.println(ErrorStrings.IO_EXCEPTION);
              }
              System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.PRELOGIN_MENU);
                return "prelogin";

            //CREATE CASE
            case "create":
                if (!validateInput(parts, 2)){
                    return "postlogin";
                }
                String gameName = parts[1];
              try {
                serverFacade.createGame(gameName);
              } catch (AuthorizationException e){
                  System.out.println(ErrorStrings.LOGOUT_UNAUTH);
              } catch (IOException e) {
                  System.out.println(ErrorStrings.IO_EXCEPTION);
              }
              return "postlogin";

            //LIST CASE
            case "list":
              try {
                ArrayList<GameData> gamesList = (ArrayList<GameData>) serverFacade.listGames();
                printGames(gamesList);
              } catch (AuthorizationException e){
                  System.out.println(ErrorStrings.LOGOUT_UNAUTH);
              } catch (IOException e) {
                  System.out.println(ErrorStrings.IO_EXCEPTION);
              }
                return "postlogin";

            //JOIN CASE
            case "join":
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

            //OBSERVE CASE
            case "observe":
                //int id = parts[1];
                //Draw chessboard
                Chessboard.main("light");
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.GAMEPLAY_MENU);
                return "game";
            default:
                return "postlogin";
        }
    }

    private static void printGames (ArrayList<GameData> gamesListArg){
        for (int i = 0; i < gamesListArg.size(); i++){
            GameData game = gamesListArg.get(i);
            System.out.println(EscapeSequences.SET_TEXT_COLOR_GREEN+
                    (i+1) + EscapeSequences.RESET_TEXT_COLOR +
                    ": " + game.gameName() + "\n   Players: " + "WHITE: " + game.whiteUsername() +
                    " BLACK: " + game.blackUsername());
        }
    }
}
