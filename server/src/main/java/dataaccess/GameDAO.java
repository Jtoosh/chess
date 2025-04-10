package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
  GameData getGameData(int gameID);
  Collection<GameData> getGameList();

  int createGame(String whiteUsername, String blackUsername, String gameName);

  void updateGame(int gameID, String teamColorRequest, String username, String game);

  void clearGameData();
}
