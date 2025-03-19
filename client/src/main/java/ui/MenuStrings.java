package ui;

public class MenuStrings {
    public static final String PRELOGINMENU = """
                    Welcome to Chess.
                    Choose one of the following:
                    
                    1. Type "help" to see info about available commands.
                    
                    2. Type "register <Username> <Password> <Email>" to register a new user.
                    
                    3. Type "login <Username> <Password>" to log in an existing user.
                    
                    4. Type "quit" to exit the program.
                    """;
    public static final String PRELOGINMENUHELP = """
                    - Help: see more info about available commands. Usage: type "help" into the terminal.
                    - Register: Add a new user to the Chess program. Usage: Type "register <Username>
                      <Password> <Email>" into the terminal, replacing the terms in braces with correct
                      values.
                    - Login: If you already have a user, this will log you in to access the Chess program.
                      Usage: type "login <Username> <Password>" into the terminal, replacing the terms in
                      braces with the correct values.
                    - Quit: This will exit the Chess program. Usage: type "quit" into the terminal.
                    """;
    public static final String POSTLOGINMENU = """
            Welcome <username>!
            
            1. Type "help" to see info about available commands.
            
            2. Type "logout" to logout of the Chess program.
            
            3. Type "create <game name>" to create a new chess game.
            
            4. Type "list all" to list all current chess games.
            
            5. Type "join <id> <WHITE|BLACK>" to join a chess game as that color.
            
            6. Type "observe <id>" to join a chess game as an observer.
            """;


}
