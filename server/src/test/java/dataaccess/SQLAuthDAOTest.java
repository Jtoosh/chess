package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
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
            TestUtilities.setUp(conn);
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
        Assertions.assertNull(authDataAccess.getAuthData("goodAuth"));
    }

    @Test
    @DisplayName("Create AuthData Positive")
    void createAuthPos(){
        AuthData createdAuth = authDataAccess.createAuth("logan");
        Assertions.assertEquals("logan", createdAuth.username());
        AuthData gotViaToken = authDataAccess.getAuthData(createdAuth.authToken());
        Assertions.assertEquals(createdAuth.authToken(),gotViaToken.authToken());
    }

    @Test
    @DisplayName("Create AuthData Negative")
    void createAuthNegative(){
        Assertions.assertThrows(IllegalArgumentException.class, ( )-> authDataAccess.createAuth("Lord Farquad"));
    }

    @Test
    @DisplayName("Clear AuthData Positive")
    void clearAuthData(){
        authDataAccess.clearAuthData();
        Assertions.assertNull(authDataAccess.getAuthData("logan"));
    }

    @Test
    @DisplayName("Delete AuthData Positive")
    void deleteAuthData(){
        AuthData createdAuth = authDataAccess.createAuth("logan");
        authDataAccess.deleteAuth(createdAuth.authToken());
        Assertions.assertNull(authDataAccess.getAuthData(createdAuth.authToken()));
    }

    @Test
    @DisplayName("Get AuthData List Positive ")
    void getAuthListPos(){
        authDataAccess.clearAuthData();
        authDataAccess.createAuth("logan");
        authDataAccess.createAuth("logan");
        authDataAccess.createAuth("logan");
        Collection <AuthData> authDataList = authDataAccess.getAuthDataList();
        Assertions.assertEquals(3, authDataList.size());
    }


    @AfterEach
    void cleanUp(){
        try(Connection conn = DatabaseManager.getConnection()){
            TestUtilities.cleanUp(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
