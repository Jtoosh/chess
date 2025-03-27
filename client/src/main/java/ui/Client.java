package ui;

import client.ServerFacade;

public class Client {
    private final ServerFacade serverFacade = new ServerFacade(8080);
    private final Repl menuDrawer = new Repl(serverFacade);
    public void run(){
        String state;
        state = "prelogin";

        System.out.println(MenuStrings.PRELOGIN_MENU);
        menuDrawer.repl(state);
    }

}
