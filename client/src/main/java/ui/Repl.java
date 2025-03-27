package ui;

import client.ServerFacade;
import ui.menu.GameMenu;
import ui.menu.PostloginMenu;
import ui.menu.PreloginMenu;

import java.util.Scanner;

public class Repl {
    private final Scanner scanner = new Scanner(System.in);
    private final ServerFacade serverFacade;

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
            }
            while (currentState.equals("postlogin")){
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "<< Chess Menu >>");
                String result = scanner.nextLine();
                currentState = PostloginMenu.eval(result, this.serverFacade);
            }
            while (currentState.equals("game")){
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "<< Game Menu >>");
                String result = scanner.nextLine();
                currentState = GameMenu.eval(result);
            }
        }

    }


}
