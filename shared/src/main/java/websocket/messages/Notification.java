package websocket.messages;

public class Notification extends ServerMessage{
  private String message;
  private boolean gameOver;
  public Notification(String messageParam, boolean gameOverArg) {
    super(ServerMessageType.NOTIFICATION);
    this.message = messageParam;
    this.gameOver = gameOverArg;
  }

  public String getMessage(){return this.message;}

  public boolean getGameOver(){return this.gameOver;}
}
