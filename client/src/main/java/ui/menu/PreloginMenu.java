package ui.menu;

import client.AlreadyInUseException;
import client.AuthorizationException;
import client.ServerFacade;
import model.AuthData;
import ui.EscapeSequences;

import java.io.IOException;

public class PreloginMenu extends ParentMenu{
    
    public static String eval(String input, ServerFacade serverFacade) {
        String parsedInput = input.strip();
        String[] parts = parsedInput.split(" ");
        switch (parts[0]) {
            //HELP CASE
            case "help":
                System.out.println(EscapeSequences.SET_TEXT_COLOR_BLUE + MenuStrings.PRELOGIN_MENU_HELP);
                return "prelogin";
            //LOGIN CASE
            case "login":
                if (!validateInput(parts, 3)){
                    return "prelogin";
                }
                String username = parts[1];
                String password = parts[2];
                try {
                    AuthData loginResult = serverFacade.login(username, password);
                }catch (IllegalArgumentException e){
                    System.out.println(ErrorStrings.BAD_LOGIN_REQUEST);
                    return "prelogin";
                } catch (AuthorizationException e){
                    System.out.println(ErrorStrings.WRONG_PASSWORD);
                    return "prelogin";
                } catch (IOException e) {
                    System.out.println(ErrorStrings.IO_EXCEPTION);
                    return "prelogin";
                }
                System.out.println("Logged in " + username + ".");
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.POSTLOGIN_MENU);
                return "postlogin";
            //REGISTER CASE
            case "register":
                if (!validateInput(parts, 4)){
                    return "prelogin";
                }
                String desiredUsername = parts[1];
                String desiredPassword = parts[2];
                String email = parts[3];
                try {
                    AuthData registerResult = serverFacade.register(desiredUsername, desiredPassword, email);
                    System.out.println("Successfully registered user: " + registerResult.username() +". Authtoken: " + registerResult.authToken());
                } catch (IllegalArgumentException e){
                    System.out.println(ErrorStrings.BAD_REGISTER_REQUEST);
                    return "prelogin";
                } catch (AlreadyInUseException e){
                   System.out.println(EscapeSequences.RESET_TEXT_COLOR +
                           "Whoops, looks like the username \"" + desiredUsername + "\" is already taken. Try a different one.");
                   return "prelogin";
                } catch (IOException e) {
                    System.out.println(ErrorStrings.IO_EXCEPTION);
                    return "prelogin";
                }
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + MenuStrings.POSTLOGIN_MENU);
                return "postlogin";
            //QUIT CASE
            case "quit":
                System.exit(0);
            //DEFAULT
            default:
                System.out.println("Sorry, we don't recognize that command. Make sure your command looks like this:\n " +
                        MenuStrings.PRELOGIN_COMMANDS);
                return "prelogin";
        }
    }

}
