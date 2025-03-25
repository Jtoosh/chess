package ui;

import client.ServerFacade;
import model.AuthData;

import java.io.IOException;

public class PreloginMenu {

    public static String eval(String input, ServerFacade serverFacade) {
        String parsedInput = input.strip();
        String[] parts = parsedInput.split(" ");
        switch (parts[0]) {
            case "help":
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + MenuStrings.PRELOGIN_MENU_HELP);
                return "prelogin";
            case "login":
                String username = parts[1];
                String password = parts[2];
                System.out.println("Logged in " + username + ".");
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.POSTLOGIN_MENU);
                return "postlogin";
            case "register":
                String desiredUsername = parts[1];
                String desiredPassword = parts[2];
                String email = parts[3];
                try {
                    AuthData registerResult = serverFacade.register(desiredUsername, desiredPassword, email);
                    System.out.println("Successfully registered user: " + registerResult.username() +". Authtoken: " + registerResult.authToken());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + "Sorry, we experienced an error connecting to the server.");
                    return "prelogin";
                }
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.POSTLOGIN_MENU);
                return "postlogin";
            case "quit":
                System.exit(0);
            default:
                System.out.println("Sorry, we don't recognize that command. Make sure your command looks like this:\n " +
                        MenuStrings.PRELOGIN_COMMANDS);
                return "prelogin";
        }
    }
}
