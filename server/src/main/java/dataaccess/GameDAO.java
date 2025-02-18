package dataaccess;

import model.GameData;

public interface GameDAO {
  public GameData getGameData(int gameID);
  public void createGame();
  public void clearGameData();
}
