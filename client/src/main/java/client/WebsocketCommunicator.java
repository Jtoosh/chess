package client;





import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.net.URI;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class WebsocketCommunicator extends Endpoint {
  private final ServerMessageObserver msgObserver;
  private Map<Integer, Collection<String>> activeClients = new TreeMap<>();
  private Session session;
  private ClientSerializer serializer = new ClientSerializer();

  public WebsocketCommunicator(ServerMessageObserver msgObserver) throws Exception{
    this.msgObserver = msgObserver;
    URI uri = new URI("ws://localhost:8080/connect");
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    this.session = container.connectToServer(this, uri);

    this.session.addMessageHandler(new MessageHandler.Whole<String>() {
      public void onMessage(String message) {
        ServerMessage serverResponse = serializer.fromJSON(message, ServerMessage.class);
        msgObserver.notify(serverResponse);
      }
    });


  }

  public void updateActiveClients(String username, Integer gameID){
//    this.activeClients.put(gameID, );
  }

  public void send(String msg) throws Exception {this.session.getBasicRemote().sendText(msg);}
  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {

  }
}
