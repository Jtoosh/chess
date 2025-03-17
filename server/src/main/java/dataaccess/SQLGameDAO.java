package dataaccess;

import chess.ChessGame;
import model.GameData;
import server.Serializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
        ArrayList<GameData> gameDataList = new ArrayList<>();
         try(Connection conn = DatabaseManager.getConnection()){
            var query = "SELECT * FROM games";
            var preppedStmt = conn.prepareStatement(query);
            ResultSet result = preppedStmt.executeQuery();
            while (result.next()){
                int retGameID = result.getInt(1);
                String retGameName = result.getString(2);
                String retWhtUsername = result.getString(3);
                String retBlkUsername = result.getString(4);
                ChessGame retGame = serializer.fromJSON(result.getString(5), ChessGame.class);
                gameDataList.add(new GameData(retGameID, retWhtUsername, retBlkUsername, retGameName, retGame));
            }
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
         return gameDataList;
    }

    @Override
    public int createGame(String whiteUsername, String blackUsername, String gameName) {
         try(Connection conn = DatabaseManager.getConnection()){
            var query = "INSERT INTO games (gameName, whiteUsername, blackUsername, ChessGame) VALUES (?, ?, ?, ?)";
            var preppedStmt = conn.prepareStatement(query);
            preppedStmt.setString(1, gameName);
            preppedStmt.setString(2, whiteUsername);
            preppedStmt.setString(3, blackUsername);
            preppedStmt.setString(4, serializer.toJSON(new ChessGame()));
            preppedStmt.executeUpdate();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage()); //Something like this could work??
        }
         return getGameList().size();
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
