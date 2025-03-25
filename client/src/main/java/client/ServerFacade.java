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
        return clientCommunicator.httpRequest(registerData, this.endpointURL + "/user", "POST", AuthData.class);
    }

    public AuthData login(String username, String password) throws IOException{
        UserData loginData = new UserData(username, password, null);
        return clientCommunicator.httpRequest(loginData, this.endpointURL + "/session", "POST", AuthData.class);
    }

    public void clear() throws IOException{
        clientCommunicator.httpRequest(null, endpointURL + "/db", "DELETE", null);
    }
}
