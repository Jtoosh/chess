package dataaccess;

import chess.ChessGame;
import model.GameData;
import server.Serializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

public class SQLGameDAO extends ParentSQLDAO implements GameDAO{
    private final Serializer serializer = new Serializer();
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
            throw new DataAccessException(e.getMessage());
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
             throw new DataAccessException(e.getMessage());
        }
         return gameDataList;
    }

    @Override
    public int createGame(String whiteUsername, String blackUsername, String gameName) {
        int gameCount = getGameList().size();
         try(Connection conn = DatabaseManager.getConnection()){
            var query = "INSERT INTO games (id, gameName, whiteUsername, blackUsername, ChessGame) VALUES (?, ?, ?, ?, ?)";
            var preppedStmt = conn.prepareStatement(query);
            preppedStmt.setInt(1, gameCount + 1);
            preppedStmt.setString(2, gameName);
            preppedStmt.setString(3, whiteUsername);
            preppedStmt.setString(4, blackUsername);
            preppedStmt.setString(5, serializer.toJSON(new ChessGame()));
            preppedStmt.executeUpdate();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage());
        }
         return gameCount + 1;
    }

    @Override
    public void updateGame(int gameID, String teamColorRequest, String username) {
      System.out.println("UPDATE EXECUTING");
        String updateTeamColor = null;
        if (teamColorRequest.equals("WHITE")){
            updateTeamColor = "whiteUsername";
        } else if (teamColorRequest.equals("BLACK")) {
            updateTeamColor = "blackUsername";
        }
        try(Connection conn = DatabaseManager.getConnection()){
            var query = String.format("UPDATE games SET %s = ? WHERE id = ?", updateTeamColor);
            var preppedStmt = conn.prepareStatement(query);
          if (username == null) {
            preppedStmt.setString(1, null);
          } else {
            preppedStmt.setString(1, username);
          }
          preppedStmt.setInt(2, gameID);
          preppedStmt.executeUpdate();
        } catch (SQLException e){
             throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clearGameData() {
        super.clearTable("games");
    }
}
