package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
  GameData getGameData(int gameID);
  Collection<GameData> getGameList();
  void createGame();
  void clearGameData();
}
