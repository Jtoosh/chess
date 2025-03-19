package ui;

import java.util.Scanner;

public class Repl {
    Scanner scanner = new Scanner(System.in);


    public void repl(String startState){
        String currentState = startState;
        while (currentState.equals("prelogin")){
            System.out.println(EscapeSequences.SET_TEXT_COLOR_YELLOW + "Login Menu >> ");
            String result = scanner.nextLine();
            currentState = PreloginMenu.eval(result);
        }
        while (currentState.equals("postlogin")){
            System.out.println(EscapeSequences.SET_TEXT_COLOR_MAGENTA + "Chess Menu >>");
            String result = scanner.nextLine();
            currentState = "postlogin";
        }
    }


}
