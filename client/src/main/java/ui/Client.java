package ui;

public class Client {
    private String STATE;
    private final Repl menu_drawer = new Repl();
    public void run(){
        STATE = "prelogin";
        String preloginMenu = """
                    Welcome to Chess.
                    Choose one of the following:
                    
                    1. Type "help" to see info about available options
                    
                    2. Type "register <Username> <Password> <Email>" to register a new user.
                    
                    3. Type "login <Username> <Password>" to log in an existing user.
                    
                    4. Type "quit" to exit the program.
                    """;
        System.out.println(preloginMenu);
        menu_drawer.repl(STATE);
    }

}
