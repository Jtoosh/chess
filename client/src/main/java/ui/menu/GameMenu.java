package ui.menu;

import ui.EscapeSequences;

public class GameMenu extends ParentMenu {

    public static String eval(String input){
        String[] parts = input.split(" ");

        return switch (parts[0]) {
            case "help" -> {
                validateInput(parts, 1);
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.GAMEPLAY_HELP);
                yield "game";
            }
            case "redraw" ->
                //redraw chess board;
                    "game";
            case "leave" -> {
                validateInput(parts, 1);
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.POSTLOGIN_MENU);
                yield "postlogin";
            }
            case "move" ->{
                    validateInput(parts, 2);
                //get square from input
                yield "game";
            }
            case "resign" -> {
                validateInput(parts, 1);
                //
                System.out.println("Well, I guess you lost now.");
                yield "game";
            }
            case "highlight" -> {
                validateInput(parts, 2);
                //get square from input
                yield "game";
            }
            default -> "game";
        };
    }

}
