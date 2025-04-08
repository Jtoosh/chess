package websocket.messages;

public class LoadGame extends ServerMessage{
  private String game;
  public LoadGame(String gameParam){
    super(ServerMessageType.LOAD_GAME);
    this.game = gameParam;
  }
}
