package ui.menu;

import client.ServerFacade;
import ui.Chessboard;
import ui.EscapeSequences;

public class PostloginMenu {

    public static String eval(String input, ServerFacade serverFacade){
        String[] parts = input.split(" ");
        switch(parts[0]){
            case "logout":
                //call logout endpoint
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.PRELOGIN_MENU);
                return "prelogin";
            case "create":
                String gameName = parts[1];
                return "postlogin";
            case "list":
                //print list
                return "postlogin";
            case "join":
                //int id = parts[1];
                String teamColor = parts[2];
                //Draw chess board
                if (teamColor.equals("WHITE")){
                    Chessboard.main("light");
                } else {
                    Chessboard.main("dark");
                }
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.GAMEPLAY_MENU);
                return "game";
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
}
