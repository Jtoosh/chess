package client;

import model.AuthData;
import model.UserData;

import java.io.IOException;

public class ServerFacade {
    private String endpointURL = "http://localhost:";
    private final ClientCommunicator clientCommunicator = new ClientCommunicator();
    private AuthData clientAuthData;


    public AuthData getClientAuthData() {
        return clientAuthData;
    }

    public ServerFacade (int port){
        this.endpointURL = this.endpointURL + port;
    }

    public AuthData register(String username, String password, String email) throws IOException {
        UserData registerData = new UserData(username, password, email);

        HttpHandler registerHandler = () -> clientCommunicator.httpRequest(registerData, this.endpointURL + "/user", "POST", AuthData.class);
        return (AuthData) handleResponse(registerHandler);
    }

    public AuthData login(String username, String password) throws IOException{
        UserData loginData = new UserData(username, password, null);
        HttpHandler loginHandler = ()-> clientCommunicator.httpRequest(loginData, this.endpointURL + "/session", "POST", AuthData.class);
        AuthData result = (AuthData) handleResponse(loginHandler);
        this.clientAuthData = result;
        return result;
    }

    public void logout() throws IOException {
        HttpHandler logoutHandler = ()-> clientCommunicator.httpRequest(this.clientAuthData, endpointURL + "/session", "DELETE", null);
        handleResponse(logoutHandler);
        this.clientAuthData = null;

    }

    public void clear() throws IOException{
        clientCommunicator.httpRequest(null, endpointURL + "/db", "DELETE", null);
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
