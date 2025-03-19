package ui;

public class PreloginMenu {

    public static String eval(String input) {
        String[] parts = input.split(" ");
        switch (parts[0]) {
            case "help":
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + MenuStrings.PRELOGINMENUHELP);
                return "prelogin";
            case "login":
                String username = parts[1];
                String password = parts[2];
                System.out.println("Logged in " + username + ".");
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.POSTLOGINMENU);
                return "postlogin";
            case "register":
                String desiredUsername = parts[1];
                String desiredPassword = parts[2];
                String email = parts[3];
                System.out.println("Registered user " + desiredUsername + ".");
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.POSTLOGINMENU);
                return "postlogin";
            case "quit":
                System.exit(0);
            default:
                return "prelogin";
        }
    }
}
