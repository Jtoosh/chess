package ui;

public class GameMenu {

    public static String eval(String input){
        String[] parts = input.split(" ");

        return switch (parts[0]) {
            case "help" -> {
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + "Help string");
                yield "game";
            }
            case "redraw" ->
                //redraw chess board;
                    "game";
            case "leave" -> {
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.POSTLOGINMENU);
                yield "postlogin";
            }
            case "move" ->
                //get square from input
                    "game";
            case "resign" -> {
                //
                System.out.println("Well, I guess you lost now.");
                yield "game";
            }
            case "highlight" ->
                //get square from input
                    "game";
            default -> "game";
        };
    }

}
