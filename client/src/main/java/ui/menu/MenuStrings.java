package ui.menu;

public class MenuStrings {
    public static final String PRELOGIN_MENU = """
                    Welcome to Chess.
                    Choose one of the following:
                    
                    1. Type \u001b[38;5;12m"help"\u001b[39m to see info about available commands.
                    
                    2. Type \u001b[38;5;12m"register <Username> <Password> <Email>"\u001b[39m to register a new user.
                    
                    3. Type \u001b[38;5;12m"login <Username> <Password>"\u001b[39m to log in an existing user.
                    
                    4. Type \u001b[38;5;12m"quit"\u001b[39m to exit the program.
                    """;
    public static final String PRELOGIN_MENU_HELP = """
                    - Help: see more info about available commands. Usage: type "help" into the terminal.
                    - Register: Add a new user to the Chess program. Usage: Type "register <Username>
                      <Password> <Email>" into the terminal, replacing the terms in braces with correct
                      values.
                    - Login: If you already have a user, this will log you in to access the Chess program.
                      Usage: type "login <Username> <Password>" into the terminal, replacing the terms in
                      braces with the correct values.
                    - Quit: This will exit the Chess program. Usage: type "quit" into the terminal.
                    """;
    public static final String PRELOGIN_COMMANDS = """
            \u001b[38;5;12m"help"\u001b - for help with commands.
            \u001b[38;5;12m"register <Username> <Password> <Email>"\u001b[39m to register a new user.
            \u001b[38;5;12m"login <Username> <Password>"\u001b[39m to log in an existing user.
            \u001b[38;5;12m"quit"\u001b[39m to exit the program.
            """;
    //Postlogin UI strings
    public static final String POSTLOGIN_MENU = """
            Welcome %s!
            
            1. Type \u001b[38;5;12m"help"\u001b[39m to see info about available commands.
            
            2. Type \u001b[38;5;12m"logout"\u001b[39m to logout of the Chess program.
            
            3. Type \u001b[38;5;12m"create <game name>"\u001b[39m to create a new chess game.
            
            4. Type \u001b[38;5;12m"list all"\u001b[39m to list all current chess games.
            
            5. Type \u001b[38;5;12m"join <id> <WHITE|BLACK>"\u001b[39m to join a chess game as that color.
            
            6. Type \u001b[38;5;12m"observe <id>"\u001b[39m to join a chess game as an observer.
            """;
    public static final String POSTLOGIN_HELP ="""
            - Type \u001b[38;5;12m"help"\u001b[39m to see info about available commands.
            - Type \u001b[38;5;12m"logout"\u001b[39m to logout of the Chess program.
            - Type \u001b[38;5;12m"create <game name>"\u001b[39m to create a new chess game.
            - Type \u001b[38;5;12m"list all"\u001b[39m to list all current chess games.
            - Type \u001b[38;5;12m"join <id> <WHITE|BLACK>"\u001b[39m to join a chess game as that color.
            - Type \u001b[38;5;12m"observe <id>"\u001b[39m to join a chess game as an observer.
            """;
    //Gameplay UI strings
    public static final String GAMEPLAY_MENU = """
            Welcome to %s!
            
            1. Type \u001b[38;5;12m"help"\u001b[39m to see info about available commands.
            
            2. Type \u001b[38;5;12m"redraw"\u001b[39m to redraw the chess board.
            
            3. Type \u001b[38;5;12m"leave"\u001b[39m to exit this game.
            
            4. Type \u001b[38;5;12m"move <start square end square>"\u001b[39m to make a move.
            
            5. Type \u001b[38;5;12m"resign"\u001b[39m to resign.
            
            6. Type \u001b[38;5;12m"highlight <square>"\u001b[39m to show legal moves for the piece on that square. 
            """;
    public static final String GAMEPLAY_HELP ="""
            - Type \u001b[38;5;12m"help"\u001b to see this menu again.
            - Type \u001b[38;5;12m"redraw"\u001b to redraw the chess board if it scrolls off-screen.
            - Type \u001b[38;5;12m"leave"\u001b to leave this game and move to the previous menu.
            - Type \u001b[38;5;12m"move <start square end square>"\u001b[39m to make a move. For example, as the white 
              player, type \u001b[38;5;12m"move d2d4"\u001b[39m to move the pawn on square d2 forward 2 squares.
            - Type \u001b[38;5;12m"resign"\u001b[39m to resign. You'll stay in this menu, but the game will be over and
              you'll be unable to make anymore moves
            - Type \u001b[38;5;12m"highlight <square>"\u001b[39m to show legal moves for that piece. For example:
              \u001b[38;5;12m"highlight f1"\u001b[39m, as the white player, will show legal moves for the Bishop on the 
              right-hand side. 
            """;


}
