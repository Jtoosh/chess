package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO{
  private ArrayList<GameData> gameList;

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
  public void createGame() {
    this.gameList.add(new GameData(10, "WhitePlaceholder", "BlackPlaceholder", "Game1", new ChessGame()));
  }

  @Override
  public void clearGameData() {
    this.gameList.clear();
  }
}
