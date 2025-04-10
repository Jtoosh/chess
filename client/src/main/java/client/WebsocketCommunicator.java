package client;





import websocket.messages.ErrorMsg;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

public class WebsocketCommunicator extends Endpoint {
  private final ServerMessageObserver msgObserver;
  private Session session;
  private final ClientSerializer serializer = new ClientSerializer();
  private String clientUsername;

  public WebsocketCommunicator(ServerMessageObserver msgObserver, int port) throws Exception{
    this.msgObserver = msgObserver;
    URI uri = new URI("ws://localhost:" + port +"/ws");
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    this.session = container.connectToServer(this, uri);

    this.session.addMessageHandler(new MessageHandler.Whole<String>() {
      public void onMessage(String message) {
        ServerMessage serverResponse = serializer.fromJSON(message, ServerMessage.class);
        msgObserver.notify(serverResponse, clientUsername);
      }
    });
  }

  public void setClientUsername(String usernameArg){this.clientUsername = usernameArg;}

  public void send(String msg) {
    try {
      this.session.getBasicRemote().sendText(msg);
    } catch (IOException e) {
      ServerMessage errorMessage = new ErrorMsg("error: problem sending message to server");
      this.msgObserver.notify(errorMessage, clientUsername);
    }
  }
  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {

  }
}
