package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SQLAuthDAO extends ParentSQLDAO implements AuthDAO{
    @Override
    public AuthData getAuthData(String username) {
        return (AuthData) super.getViaUsername(username, "authData");
    }

    @Override
    public AuthData createAuth(String username) {
        try(Connection conn = DatabaseManager.getConnection()){
            var query = "INSERT INTO authData (authToken, username) VALUES (?, ?)";
            var preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            return getAuthData(username);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public Collection<AuthData> getAuthDataList() {
        try(Connection conn = DatabaseManager.getConnection()){
            String query = "SELECT * FROM authData";
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        return List.of();
    }

    @Override
    public void deleteAuth(String authToken) {
        try(Connection conn = DatabaseManager.getConnection()){
            String query = String.format("DELETE FROM authData WHERE authToken = %s", authToken);
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
    }

    @Override
    public void clearAuthData() {
        super.clearTable("authData");
    }
}
