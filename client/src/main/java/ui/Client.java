package ui;

import chess.ChessBoard;
import client.ServerFacade;
import client.ServerMessageObserver;
import model.GameData;
import ui.menu.GameMenu;
import ui.menu.MenuStrings;
import websocket.messages.*;

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
    public void notify(ServerMessage message, String clientUsername) {
        switch (message.getServerMessageType()){
            case NOTIFICATION :
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + ((Notification) message).getMessage());
                GameMenu.setGameIsOver(((Notification) message).getGameOver());
                break;
            case LOAD_GAME:
                LoadGame gameMessage = (LoadGame) message;
                ChessBoard boardToLoad = gameMessage.getGame().game().getBoard();
                String startColor = evalBoardStartColor(gameMessage.getGame(), clientUsername);
                Chessboard.draw(startColor, boardToLoad,null );
                menuDrawer.setGameData(gameMessage.getGame());
                break;
            case ERROR:
                System.out.println(EscapeSequences.RESET_TEXT_COLOR + ((ErrorMsg) message).getErrorMessage());
                break;
        }
    }

    private String evalBoardStartColor(GameData gameToObserve, String usernameToCheck){
       if (usernameToCheck.equals(gameToObserve.blackUsername())){
           return "dark";
       }
       return "light";
    }
}
