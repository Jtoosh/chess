package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
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
            String authToken = UUID.randomUUID().toString();
            preparedStatement.setString(1, authToken);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            return new AuthData(authToken, username);
        } catch (SQLException e){
            if (e.getSQLState().equals("23000")){
                throw new IllegalArgumentException("Error: user not found");
            }else {
                throw new DataAccessException(e.getMessage());
            }
        }
    }

    @Override
    public Collection<AuthData> getAuthDataList() {
        Collection<AuthData> authDataList = new ArrayList<>();
        try(Connection conn = DatabaseManager.getConnection()){
            String query = "SELECT * FROM authData";
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            while (result.next()){
                String retrievedAuthToken = result.getString(1);
                String retrievedUsername = result.getString(2);
                authDataList.add(new AuthData(retrievedAuthToken, retrievedUsername));
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        return authDataList;
    }

    @Override
    public void deleteAuth(String authToken) {
        try(Connection conn = DatabaseManager.getConnection()){
            var query = "DELETE FROM authData WHERE authToken = ?";
            var preppedStmt = conn.prepareStatement(query);
            preppedStmt.setString(1, authToken);
            preppedStmt.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
    }

    @Override
    public void clearAuthData() {
        super.clearTable("authData");
    }
}
