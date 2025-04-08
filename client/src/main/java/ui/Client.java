package ui;

import client.ServerFacade;
import client.ServerMessageObserver;
import ui.menu.MenuStrings;
import websocket.messages.Notification;
import websocket.messages.ServerMessage;

public class Client implements ServerMessageObserver {
    private final ServerFacade serverFacade = new ServerFacade(8080, this);
    private final Repl menuDrawer = new Repl(serverFacade);
    public void run(){
        String state;
        state = "prelogin";

        System.out.println(MenuStrings.PRELOGIN_MENU);
        menuDrawer.repl(state);
    }

    @Override
    public void notify(ServerMessage message) {
        System.out.println(message.getServerMessageType());
        switch (message.getServerMessageType()){
            case NOTIFICATION :
                System.out.println(((Notification) message).getMessage());
        }
    }
}
