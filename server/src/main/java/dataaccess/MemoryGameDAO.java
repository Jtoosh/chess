package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
  private final ArrayList<GameData> gameList = new ArrayList<>();

  @Override
  public GameData getGameData(int gameID) {
    for (GameData game : gameList){
      if (game.gameID() == gameID){
        return game;
      }
    }
    return null;
  }

  @Override
  public Collection<GameData> getGameList() {
    return gameList;
  }

  @Override
  public int createGame(String whiteUsername, String blackUsername, String gameName) {
    this.gameList.add(new GameData(this.gameList.size() + 1, whiteUsername, blackUsername, gameName, new ChessGame()));
    return this.gameList.size();
  }

  @Override
  public void updateGame(int gameID, String teamColorRequest, String username) {
    GameData gameToUpdate = getGameData(gameID);
    GameData updatedGame = (teamColorRequest.equals("WHITE")) ? new GameData(gameToUpdate.gameID(), username, gameToUpdate.blackUsername(), gameToUpdate.gameName(), gameToUpdate.game())
            : new GameData(gameToUpdate.gameID(), gameToUpdate.whiteUsername(), username, gameToUpdate.gameName(), gameToUpdate.game());
    gameList.remove(gameToUpdate);
    gameList.add(updatedGame);
  }

  @Override
  public boolean clearGameData() {
//    if (!this.gameList.isEmpty()){
//      this.gameList.clear();
//      return true;
//    } else {return false;}
    this.gameList.clear();
    return true;
  }
}
