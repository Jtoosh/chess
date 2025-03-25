package ui;

import client.ServerFacade;

import java.util.Scanner;

public class Repl {
    Scanner scanner = new Scanner(System.in);

    public void repl(String startState){
        String currentState = startState;
        while (true){
            while (currentState.equals("prelogin")){
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "<< Login Menu >> ");
                String result = scanner.nextLine();
                currentState = PreloginMenu.eval(result);
            }
            while (currentState.equals("postlogin")){
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "<< Chess Menu >>");
                String result = scanner.nextLine();
                currentState = PostloginMenu.eval(result);
            }
            while (currentState.equals("game")){
                System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "<< Game Menu >>");
                String result = scanner.nextLine();
                currentState = GameMenu.eval(result);
            }
        }

    }


}
