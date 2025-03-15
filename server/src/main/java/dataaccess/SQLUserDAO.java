package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

public class SQLUserDAO implements UserDAO{
    @Override
    public UserData getUserData(String username) {
        try(Connection conn = DatabaseManager.getConnection()){
            var query = "SELECT * FROM users WHERE username = ?";
            var stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();
            boolean emptyStatus = !result.next(); //True if ResultSet is empty
            if (emptyStatus) {
                throw new IllegalArgumentException("Error: user not found");
            } else{
                String retrievedUsername = result.getString(1);
                String retrievedPasswordHash = result.getString(2);
                String retrievedEmail = result.getString(3);
                return new UserData(retrievedUsername, retrievedPasswordHash,retrievedEmail);
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void createUser(String username, String password, String email) {
         try(Connection conn = DatabaseManager.getConnection()){
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            var preppedStmt = conn.prepareStatement(query);
            preppedStmt.setString(1, username);
            preppedStmt.setString(2, hashedPassword);
            preppedStmt.setString(3, email);
            int result = preppedStmt.executeUpdate();
            if (result == 0){
                throw new IllegalArgumentException("Error: bad request");
            }
        } catch (SQLException e){
             if (e.getMessage().equals(String.format("Duplicate entry '%s' for key 'users.PRIMARY'", username))){
                 throw new AlreadyInUseException("Error: already in use");
             } else{
                 throw new DataAccessException(e.getMessage());
             }
        }
    }

    @Override
    public void clearUserData() {
        try(Connection conn = DatabaseManager.getConnection()){
            String query = "DELETE FROM users";
            var stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
    }

    @Override
    public Collection<UserData> getUserList() {
        return List.of();
        /* try(Connection conn = DatabaseManager.getConnection()){
            String query = "SELECT FROM users;
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }*/
    }
}
