package ui;

import client.ServerFacade;
import model.GameData;
import ui.menu.*;

import java.util.Scanner;

public class Repl {
    private final Scanner scanner = new Scanner(System.in);
    private final ServerFacade serverFacade;
    private String username;
    private GameData gameData;
    private boolean isObserver;

    public Repl (ServerFacade serverFacadeArg){
        this.serverFacade = serverFacadeArg;
    }

    public void repl(String startState){
        String currentState = startState;
        while (true){
            while (currentState.equals("prelogin")){
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "<< Login Menu >> ");
                String result = scanner.nextLine();
                currentState = PreloginMenu.eval(result, this.serverFacade);
                username = PreloginMenu.returnUsername();
            }
            while (currentState.equals("postlogin")){
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "<< Chess Menu >>");
                String result = scanner.nextLine();
                currentState = PostloginMenu.eval(result, this.serverFacade);
                gameData = PostloginMenu.getGameData();
                isObserver = PostloginMenu.getRequestsObserverFlag();
            }
            while (currentState.equals("game")){
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "<< Game Menu >>");
                String result = scanner.nextLine();
                GameMenu.setIsObserver(isObserver);
                currentState = GameMenu.eval(result, this.serverFacade, username, gameData);
            }
        }

    }

    public void setGameData(GameData newGameData){this.gameData = newGameData;}


}
