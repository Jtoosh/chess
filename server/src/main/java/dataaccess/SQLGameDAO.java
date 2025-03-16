package dataaccess;

import model.GameData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

public class SQLGameDAO extends ParentSQLDAO implements GameDAO{
    @Override
    public GameData getGameData(int gameID) {
        try(Connection conn = DatabaseManager.getConnection()){
            String query = String.format("SELECT * FROM games WHERE ID = %n", gameID);
            Statement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }

        return null;
    }

    @Override
    public Collection<GameData> getGameList() {
        /* try(Connection conn = DatabaseManager.getConnection()){
            String query = "SELECT FROM games;
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }*/
        return List.of();
    }

    @Override
    public int createGame(String whiteUsername, String blackUsername, String gameName) {
        return 0;
        /* try(Connection conn = DatabaseManager.getConnection()){
            String query = "INSERT into games (whiteUsername, blackUsername, gameName) VALUES (%s, %s, %s)", whiteUsername, blackUsername, gameName;  //gameID will be an auto-incrementing pk
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }*/
    }

    @Override
    public void updateGame(int gameID, String teamColorRequest, String username) {
        /* try(Connection conn = DatabaseManager.getConnection()){
        //Some code to determine the requested color and put it in the query as variable teamColorUsername
            String query = "UPDATE games SET %s = %s WHERE gameID = %n". teamColorUsername, username, gameID
            stmt = conn.prepareStatement(query);
            result = stmt.executeQuery();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }*/
    }

    @Override
    public void clearGameData() {
        super.clearTable("games");
    }
}
