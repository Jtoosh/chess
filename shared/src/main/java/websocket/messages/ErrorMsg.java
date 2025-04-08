package websocket.messages;

public class ErrorMsg extends ServerMessage{
  private String errorMessage;
  public ErrorMsg(String errorMessageParam){
    super(ServerMessageType.ERROR);
    this.errorMessage = errorMessageParam;
  }
}
