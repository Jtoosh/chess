package ui;

import client.ServerFacade;

public class Client {
    private final Repl menuDrawer = new Repl();
    private final ServerFacade serverFacade = new ServerFacade(8080);
    public void run(){
        String STATE;
        STATE = "prelogin";

        System.out.println(MenuStrings.PRELOGIN_MENU);
        menuDrawer.repl(STATE);
    }

}
