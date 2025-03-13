package dataaccess;

import org.junit.jupiter.api.*;

import java.sql.*;

public class UserDAOTest {
//    @BeforeAll
//    static void createDB(){
//        DatabaseManager.createDatabase();
//    }

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
    void getUserTest(){
        try (Connection conn = DatabaseManager.getConnection()){
            var getStatement = "SELECT * FROM users WHERE username = ?";
            var preppedStmt = conn.prepareStatement(getStatement);
            preppedStmt.setString(1, "logan");
            ResultSet result = preppedStmt.executeQuery();
            result.next();
            Assertions.assertEquals("logan", result.getString(1));
            Assertions.assertEquals("password", result.getString(2));
            Assertions.assertEquals("email.com", result.getString(3));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void cleanUp(){
        try(Connection conn = DatabaseManager.getConnection()){
            var cleanUpStatement = "DELETE FROM users WHERE username = ?";
            var preppedCleanUp = conn.prepareStatement(cleanUpStatement);
            preppedCleanUp.setString(1, "logan");
            preppedCleanUp.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
