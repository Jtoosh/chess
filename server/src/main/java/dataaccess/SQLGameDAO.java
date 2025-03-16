package dataaccess;

import chess.ChessGame;
import model.GameData;
import server.Serializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

public class SQLGameDAO extends ParentSQLDAO implements GameDAO{
    Serializer serializer = new Serializer();
    @Override
    public GameData getGameData(int gameID) {
        try(Connection conn = DatabaseManager.getConnection()){
            var query = "SELECT * FROM games WHERE id = ?";
            var preppedStmt = conn.prepareStatement(query);
            preppedStmt.setInt(1, gameID);
            ResultSet result = preppedStmt.executeQuery();
            boolean emptyStatus = !result.next();

            if (emptyStatus) {return null;}

            int retGameID = result.getInt(1);
            String retGameName = result.getString(2);
            String retWhtUsername = result.getString(3);
            String retBlkUsername = result.getString(4);
            ChessGame retGame = serializer.fromJSON(result.getString(5), ChessGame.class);
            return new GameData(retGameID, retWhtUsername, retBlkUsername, retGameName, retGame);
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
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
