package ui;

import client.AlreadyInUseException;
import client.AuthorizationException;
import client.HttpHandler;
import client.ServerFacade;

import java.io.IOException;

public class Client {
    private final ServerFacade serverFacade = new ServerFacade(8080);
    private final Repl menuDrawer = new Repl(serverFacade);
    public void run(){
        String state;
        state = "prelogin";

        System.out.println(MenuStrings.PRELOGIN_MENU);
        menuDrawer.repl(state);
    }

    private Record handleResponse(HttpHandler commHandler) throws IOException {
        try{
            return commHandler.makeRequest();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e);
        } catch (AuthorizationException e){
            throw new AuthorizationException(e.getMessage());
        } catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        } catch (IOException e){
            throw new IOException(e);
        }
    }
}
