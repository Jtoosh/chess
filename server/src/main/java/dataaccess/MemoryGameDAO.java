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
  public void createGame() {
    this.gameList.add(new GameData(10, "WhitePlaceholder", "BlackPlaceholder", "Game1", new ChessGame()));
  }

  @Override
  public void clearGameData() {
    if (this.gameList != null){
      this.gameList.clear();
    }
  }
}
