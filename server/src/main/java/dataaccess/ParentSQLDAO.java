package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParentSQLDAO {


    public Record getViaUsername(String username, String table) {
        try (Connection conn = DatabaseManager.getConnection()) {
            var query = "";
            PreparedStatement stmt;
            if (table.equals("authData")){
                query = "SELECT * FROM authData WHERE username = ? OR authToken = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, username);
            } else{
                query = String.format("SELECT * FROM %s WHERE username = ?", table);
                stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
            }
            ResultSet result = stmt.executeQuery();
            boolean emptyStatus = !result.next(); //True if ResultSet is empty
            if (emptyStatus) {
                throw new IllegalArgumentException("Error: not found");
            } else {
                switch (table){
                    case "users":
                        String retrievedUsername = result.getString(1);
                        String retrievedPasswordHash = result.getString(2);
                        String retrievedEmail = result.getString(3);
                        return new UserData(retrievedUsername, retrievedPasswordHash, retrievedEmail);
                    case "authData":
                        String retrievedFKUsername = result.getString(1);
                        String retrievedAuthToken = result.getString(2);
                        return new AuthData(retrievedFKUsername, retrievedAuthToken);
                    default:
                        return null;
                }
            }
        } catch(SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    public void clearTable(String table){
        try(Connection conn = DatabaseManager.getConnection()){
            var query = String.format("DELETE FROM %s", table);
            var preparedStatement = conn.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
    }
}
