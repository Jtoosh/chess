package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAOTest {
    private final AuthDAO authDataAccess = new SQLAuthDAO();

    @BeforeAll
    static void createDB(){
        DatabaseManager.createDatabase(); //Creates the DB if needed
    }

    @BeforeEach
    void setup(){
        try(Connection conn = DatabaseManager.getConnection(); ){
            var setUpStatement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            var preppedStmt = conn.prepareStatement(setUpStatement);
            preppedStmt.setString(1, "logan");
            preppedStmt.setString(2, "password");
            preppedStmt.setString(3, "email.com");
            preppedStmt.executeUpdate();

            var setUpStatement2 = "INSERT INTO authData (username, authToken) VALUES (?, ?)";
            var preppedStmt2 = conn.prepareStatement(setUpStatement2);
            preppedStmt2.setString(1, "logan");
            preppedStmt2.setString(2, UUID.randomUUID().toString());
            preppedStmt2.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Test
    @DisplayName("Get AuthData Positive with Username")
    void getAuthDataPos(){
        AuthData actualAuth = authDataAccess.getAuthData("logan");
        Assertions.assertEquals("logan", actualAuth.username());
        Assertions.assertNotNull(actualAuth.authToken());
    }

    @Test
    @DisplayName("Get AuthData Positive with AuthToken")
    void getAuthDataPos2(){
        AuthData actualAuth = authDataAccess.getAuthData("logan");
        AuthData getWithToken = authDataAccess.getAuthData(actualAuth.authToken());
        Assertions.assertEquals("logan", getWithToken.username());
        Assertions.assertEquals(actualAuth.authToken(), getWithToken.authToken());
    }

    @Test
    @DisplayName("Get AuthData Negative")
    void getAuthDataNegative(){
        Assertions.assertThrows(IllegalArgumentException.class, ()-> authDataAccess.getAuthData("goodAuth"));
    }

    @AfterEach
    void cleanUp(){
        try(Connection conn = DatabaseManager.getConnection()){
            String[] usersMade = {"logan"};
            for (String username : usersMade) {
                var cleanUpStatement = "DELETE FROM users WHERE username = ?";
                var preppedCleanUp = conn.prepareStatement(cleanUpStatement);
                preppedCleanUp.setString(1, username);
                preppedCleanUp.executeUpdate();

                var cleanUpStatement2 = "DELETE FROM authData WHERE username = ?";
                var preppedCleanUp2 = conn.prepareStatement(cleanUpStatement2);
                preppedCleanUp.setString(1, username);
                preppedCleanUp.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
