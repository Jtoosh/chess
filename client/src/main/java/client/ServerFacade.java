package client;

import model.AuthData;
import model.UserData;

import java.io.IOException;

public class ServerFacade {
    private String endpointURL = "http://localhost:";
    private final ClientCommunicator clientCommunicator = new ClientCommunicator();

    public ServerFacade (int port){
        this.endpointURL = this.endpointURL + port;
    }

    public AuthData register(String username, String password, String email) throws IOException {
        UserData registerData = new UserData(username, password, email);
//        System.out.println(this.endpointURL + "/user");
        HttpHandler registerHandler = () -> clientCommunicator.httpRequest(registerData, this.endpointURL + "/user", "POST", AuthData.class);
        return (AuthData) handleResponse(registerHandler);
    }

    public AuthData login(String username, String password) throws IOException{
        UserData loginData = new UserData(username, password, null);

        return clientCommunicator.httpRequest(loginData, this.endpointURL + "/session", "POST", AuthData.class);
    }

    public void clear() throws IOException{
        clientCommunicator.httpRequest(null, endpointURL + "/db", "DELETE", null);
    }

    private Record handleResponse(HttpHandler commHandler) throws IOException {
        try{
            Record result = commHandler.makeRequest();
            return result;
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
