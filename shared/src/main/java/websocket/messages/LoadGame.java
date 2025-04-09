package websocket.messages;

import model.GameData;

public class LoadGame extends ServerMessage{
  private GameData game;
  public LoadGame(GameData gameParam){
    super(ServerMessageType.LOAD_GAME);
    this.game = gameParam;
  }
}
