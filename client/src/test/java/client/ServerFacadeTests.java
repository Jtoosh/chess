package client;

import model.AuthData;
import org.junit.jupiter.api.*;
import server.Server;

import java.io.IOException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        serverFacade = new ServerFacade(port);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    @DisplayName("Register Positive")
    void registerPos() throws IOException {
        AuthData registerResponse = serverFacade.register("rayquon", "rayqwan rocks", "rayquon.com");
        Assertions.assertEquals("rayquon", registerResponse.username());
        Assertions.assertNotNull(registerResponse.authToken());
    }

    @Test
    @DisplayName("Register Negative")
    void registerNegative() throws IOException{
        Assertions.assertThrows(AlreadyInUseException.class, ()-> serverFacade.register("rayquon", "", ""));
    }

//    @Test
//    @DisplayName("Login Positive")
//    void loginPos() throws IOException{
//        AuthData loginResponse = serverFacade.login()
//    }

}
