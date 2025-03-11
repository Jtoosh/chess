package dataaccess;

import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class SQLUserDAO implements UserDAO{
    @Override
    public UserData getUserData(String username) {
       /* try(Connection conn = DatabaseManager.getConnection()){
            String query = `SELECT * FROM USERS WHERE USER = $username`;
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){

        }*/

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

        }*/
    }

    @Override
    public void clearUserData() {
       /* try(Connection conn = DatabaseManager.getConnection()){
            String query = "DELETE FROM users";
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){

        }*/
    }

    @Override
    public Collection<UserData> getUserList() {
        return List.of();
        /* try(Connection conn = DatabaseManager.getConnection()){
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String query = "SELECT FROM users;
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){

        }*/
    }
}
