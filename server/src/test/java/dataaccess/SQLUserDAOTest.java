package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Collection;

class SQLUserDAOTest {

    private final UserDAO userDataAccess = new SQLUserDAO();

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
        Assertions.assertNull(userDataAccess.getUserData("nonExistent"));
    }

    @Test
    @DisplayName("Create User Positive")
    void createUserTestPos(){
        userDataAccess.createUser("rogan", "james the b3st", "email.com");
        UserData result = userDataAccess.getUserData("rogan");
        Assertions.assertEquals("rogan", result.username());
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
        userDataAccess.clearUserData();
        Assertions.assertNull(userDataAccess.getUserData("logan"));
    }

    @Test
    @DisplayName("Get User List Positive")
    void getUserList(){
        Collection<UserData> userList = userDataAccess.getUserList();
        Assertions.assertTrue(userList.size() == 3);
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
