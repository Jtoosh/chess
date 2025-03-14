package dataaccess;

import model.UserData;

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
            boolean emptyStatus = !result.next();
            if (emptyStatus) {
                throw new IllegalArgumentException("Error: user not found");
            } else{
                return new UserData(result.getString(1), result.getString(2), result.getString(3));
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
    }

    @Override
    public void createUser(String username, String password, String email) {
        /* try(Connection conn = DatabaseManager.getConnection()){
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String query = `INSERT INTO users VALUES($username, $hashedPassword, $email`;
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }*/
    }

    @Override
    public void clearUserData() {
       /* try(Connection conn = DatabaseManager.getConnection()){
            String query = "DELETE FROM users";
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }*/
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
