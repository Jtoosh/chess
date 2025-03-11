package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO{
    @Override
    public GameData getGameData(int gameID) {
        return null;
    }

    @Override
    public Collection<GameData> getGameList() {
        return List.of();
    }

    @Override
    public int createGame(String whiteUsername, String blackUsername, String gameName) {
        return 0;
    }

    @Override
    public void updateGame(int gameID, String teamColorRequest, String username) {

    }

    @Override
    public void clearGameData() {

    }
}
