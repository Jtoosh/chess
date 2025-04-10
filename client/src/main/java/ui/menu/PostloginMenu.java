package ui.menu;

import client.AlreadyInUseException;
import client.AuthorizationException;
import client.ServerFacade;
import model.GameData;
import ui.Chessboard;
import ui.EscapeSequences;
import ui.menu.handlers.JoinHandler;
import ui.menu.handlers.ObserveHandler;

import java.io.IOException;
import java.util.ArrayList;

public class PostloginMenu extends ParentMenu{

    private static GameData classGameData;
    private static boolean requestsObserver = false;

    public static String eval(String input, ServerFacade serverFacade){
        String[] parts = input.split(" ");
        switch(parts[0]){

           //HELP CASE
          case "help":
            System.out.println(MenuStrings.POSTLOGIN_HELP);
            return "postlogin";
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
              String joinResult = JoinHandler.joinHandle(parts, serverFacade);
              classGameData = JoinHandler.getGameData();
              requestsObserver = false;
              return joinResult;


            //OBSERVE CASE
            case "observe":
              String observeResult = ObserveHandler.observeHandle(parts, serverFacade);
              classGameData = ObserveHandler.getGameData();
              requestsObserver = true;
              return observeResult;
            default:
              System.out.println("Sorry, we don't recognize that command. Make sure your command looks like this:\n " +
                      MenuStrings.POSTLOGIN_HELP);
                return "postlogin";
        }
    }

  public static GameData getGameData(){
    return classGameData;
  }
  public static boolean getRequestsObserverFlag(){return requestsObserver;}

}
