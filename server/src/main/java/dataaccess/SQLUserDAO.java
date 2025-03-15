package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLUserDAO extends ParentSQLDAO implements UserDAO{
    @Override
    public UserData getUserData(String username) {
         return (UserData) super.getViaUsername(username, "users");
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
        super.clearTable("users");
    }

    @Override
    public Collection<UserData> getUserList() {
         try(Connection conn = DatabaseManager.getConnection()){
            var query = "SELECT * FROM users";
            var stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
             ArrayList<UserData> userDataList = new ArrayList<>();
            while (result.next()){
                String retrievedUsername = result.getString(1);
                String retrievedPasswordHash = result.getString(2);
                String retrievedEmail = result.getString(3);
                userDataList.add(new UserData(retrievedUsername, retrievedPasswordHash,retrievedEmail));
            }
            return userDataList;
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
    }
}
