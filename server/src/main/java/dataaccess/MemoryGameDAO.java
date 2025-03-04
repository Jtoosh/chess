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
  public boolean clearGameData() {
//    if (!this.gameList.isEmpty()){
//      this.gameList.clear();
//      return true;
//    } else {return false;}
    this.gameList.clear();
    return true;
  }
}
