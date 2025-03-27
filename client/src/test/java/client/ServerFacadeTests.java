package client;

import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;

import java.io.IOException;
import java.util.Collection;


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
    void loginNegative(){
        Assertions.assertThrows(AuthorizationException.class,
                ()-> serverFacade.login("testUserNotFound", "testPass"));
        Assertions.assertThrows(AuthorizationException.class,
                ()-> serverFacade.login("testUser", "wrongTestPassword"));
    }

    @Test
    @DisplayName("Logout Positive")
    void logoutPos() throws IOException {
        serverFacade.login("testUser", "testPassword");
        serverFacade.logout();
        Assertions.assertNull(serverFacade.getClientAuthData().authToken());
    }

    @Test
    @DisplayName("Logout Negative")
    void logoutNegative(){
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

    @Test
    @DisplayName("Create Game Negative")
    void createGameNegative() {
        Assertions.assertThrows(AuthorizationException.class,
                ()-> serverFacade.createGame("My game"));
    }

    @Test
    @DisplayName("List Game Positive")
    void listGamePos() throws IOException {
        serverFacade.login("testUser", "testPassword");
        Collection<GameData> gamesList = serverFacade.listGames();
        Assertions.assertEquals(0, gamesList.size());
    }

    @Test
    @DisplayName("List Games Negative")
    void listGamesNegative() {
        Assertions.assertThrows(AuthorizationException.class,
                ()-> serverFacade.listGames());
    }

    @Test
    @DisplayName("Join Game Positive")
    void joinGamePos() throws IOException {
        serverFacade.login("testUser", "testPassword");
        serverFacade.createGame("My game");
        serverFacade.joinGame(1, "WHITE");
        Collection<GameData> gamesList = serverFacade.listGames();
        GameData joinedGame = TestUtilities.findByID(gamesList, 1);
        Assertions.assertEquals("testUser", joinedGame.whiteUsername());
    }

    @Test
    @DisplayName("List Games Negative")
    void joinGameNegative() throws IOException {
        Assertions.assertThrows(AuthorizationException.class,
                ()-> serverFacade.joinGame(1, "BLACK"));
        serverFacade.login("testUser", "testPassword");
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> serverFacade.joinGame(1, "GREEN"));
        serverFacade.createGame("My game");
        serverFacade.joinGame(1, "WHITE");
        Assertions.assertThrows(AlreadyInUseException.class,
                ()-> serverFacade.joinGame(1, "WHITE"));
    }

}
