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
            String query = String.format("SELECT * FROM USERS WHERE USER = %s", username);
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }

        return null;
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
