package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
  public GameData getGameData(int gameID);
  public Collection<GameData> getGameList();
  public void createGame();
  public void clearGameData();
}
