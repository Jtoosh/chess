package client;

import model.GameData;

import java.util.Collection;

public class TestUtilities {

    public static GameData findByID(Collection<GameData> collection, int id){
        for (GameData game : collection){
            if (game.gameID() == id){
                return  game;
            }
        }
        return null;
    }
}
