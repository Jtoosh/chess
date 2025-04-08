package websocket.messages;

public class Notification extends ServerMessage{
  private String message;
  public Notification(String messageParam) {
    super(ServerMessageType.NOTIFICATION);
    this.message = messageParam;
  }
}
