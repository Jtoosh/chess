package ui;

public class PostloginMenu {

    public static String eval(String input){
        String[] parts = input.split(" ");
        switch(parts[0]){
            case "logout":
                //call logout endpoint
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.PRELOGINMENU);
                return "prelogin";
            case "create":
                String gameName = parts[1];
                return "postlogin";
            case "list":
                //print list
                return "postlogin";
            case "join":
                //int id = parts[1];
                //String teamColor = parts[2]
                //Draw chess board
                Chessboard.main();
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.GAMEPLAYMENU);
                return "game";
            case "observe":
                //int id = parts[1];
                //Draw chessboard
                Chessboard.main();
                System.out.print(EscapeSequences.RESET_BG_COLOR);
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.GAMEPLAYMENU);
                return "game";
            default:
                return "postlogin";
        }
    }
}
