package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAOTest {

    private UserDAO userDataAccess = new SQLUserDAO();

    @BeforeEach
    void setup(){
        try(Connection conn = DatabaseManager.getConnection(); ){
            var setUpStatement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            var preppedStmt = conn.prepareStatement(setUpStatement);
            preppedStmt.setString(1, "logan");
            preppedStmt.setString(2, "password");
            preppedStmt.setString(3, "email.com");
            preppedStmt.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Test
    @DisplayName("Get User Positive")
    void getUserTestPos(){
            UserData result = userDataAccess.getUserData("logan");
            Assertions.assertEquals("logan", result.username());
            Assertions.assertEquals("password", result.password());
            Assertions.assertEquals("email.com", result.email());

    }

    @Test
    @DisplayName("Get User Negative")
    void getUserTestNegative(){
        Assertions.assertThrows(IllegalArgumentException.class, ( ) -> userDataAccess.getUserData("nonExistent"));
    }

    @Test
    @DisplayName("Create User Positive")
    void createUserTestPos(){
        userDataAccess.createUser("logdog", "james the b3st", "email.com");
        UserData result = userDataAccess.getUserData("logdog");
        Assertions.assertEquals("logdog", result.username());
        Assertions.assertTrue(BCrypt.checkpw("james the b3st", result.password()));
        Assertions.assertEquals("email.com", result.email());
    }

    @Test
    @DisplayName("Create User Negative")
    void createUserTestNegative(){
        Assertions.assertThrows(AlreadyInUseException.class, ()-> userDataAccess.createUser("logan", "newPassword", "newemail@email.com"));
    }

    @Test
    @DisplayName("Clear Users Positive")
    void clearUsersTest(){

    }

    @AfterEach
    void cleanUp(){
        try(Connection conn = DatabaseManager.getConnection()){
            String[] usersMade = {"logan", "logdog"};
            for (String username : usersMade) {
                var cleanUpStatement = "DELETE FROM users WHERE username = ?";
                var preppedCleanUp = conn.prepareStatement(cleanUpStatement);
                preppedCleanUp.setString(1, username);
                preppedCleanUp.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
