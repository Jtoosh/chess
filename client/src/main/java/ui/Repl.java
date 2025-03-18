package ui;

import java.util.Scanner;

public class Repl {
    Scanner scanner = new Scanner(System.in);
    private final String preloginMenuHelp = """
                    - Help: see more info about available commands. Usage: type "help" into the terminal.
                    - Register: Add a new user to the Chess program. Usage: Type "register <Username>
                      <Password> <Email>" into the terminal, replacing the terms in braces with correct
                      values.
                    - Login: If you already have a user, this will log you in to access the Chess program.
                      Usage: type "login <Username> <Password>" into the terminal, replacing the terms in
                      braces with the correct values.
                    - Quit: This will exit the Chess program. Usage: type "quit" into the terminal.
                    """;

    public void repl(String state){
        while (state.equals("prelogin")){
            String result = scanner.nextLine();
            eval(result);
        }
    }

    private void eval(String input){
        switch (input){
            case "help":
                System.out.println(preloginMenuHelp);
                break;
            case "login":
                System.out.println();
            case "quit":
                System.exit(0);
        }

    }


}
