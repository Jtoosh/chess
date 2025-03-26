package client;

import model.AuthData;
import org.junit.jupiter.api.*;
import response.CreateResponse;
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

    @BeforeEach
    void setUp() throws IOException {
        serverFacade.register("testUser", "testPassword", "testEmail.com");
    }

    @AfterEach
    void cleanUp() throws IOException{
        serverFacade.clear();
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
        Assertions.assertThrows(AlreadyInUseException.class,
                ()-> serverFacade.register("testUser", "", ""));
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> serverFacade.register("testUser4", null, "email"));
    }

    @Test
    @DisplayName("Login Positive")
    void loginPos() throws IOException {
        AuthData loginResponse = serverFacade.login("testUser", "testPassword");
        Assertions.assertEquals("testUser", loginResponse.username());
        Assertions.assertNotNull(loginResponse.authToken());
    }

    @Test
    @DisplayName("Login Negative")
    void LoginNegative(){
        Assertions.assertThrows(AuthorizationException.class,
                ()-> serverFacade.login("testUserNotFound", "testPass"));
        Assertions.assertThrows(AuthorizationException.class,
                ()-> serverFacade.login("testUser", "wrongTestPassword"));
    }

    @Test
    @DisplayName("Logout Positive")
    void LogoutPos() throws IOException {
        serverFacade.login("testUser", "testPassword");
        serverFacade.logout();
        Assertions.assertNull(serverFacade.getClientAuthData());
    }

    @Test
    @DisplayName("Logout Negative")
    void LogoutNegative(){
        Assertions.assertThrows(
                AuthorizationException.class, ()-> serverFacade.logout());
    }

    @Test
    @DisplayName("Create Game Positive")
    void createGamePos() throws IOException {
        serverFacade.login("testUser", "testPassword");
        int gameID = serverFacade.createGame("My game");
        Assertions.assertEquals(1, gameID);
    }

}
