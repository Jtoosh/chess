package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO{
    @Override
    public AuthData getAuthData(String username) {
        try(Connection conn = DatabaseManager.getConnection()){
            String query = String.format("SELECT * FROM authData WHERE USER = %s", username);
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
        return null;
    }

    @Override
    public AuthData createAuth(String username) {
        try(Connection conn = DatabaseManager.getConnection()){
            String query = String.format("INSERT INTO authData (authToken, username) VALUES (%s, %s)", UUID.randomUUID().toString(), username);
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
        return null;
    }

    @Override
    public Collection<AuthData> getAuthDataList() {
        try(Connection conn = DatabaseManager.getConnection()){
            String query = "SELECT * FROM authData";
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
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
        try(Connection conn = DatabaseManager.getConnection()){
            String query = "DELETE FROM authData";
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
    }
}
