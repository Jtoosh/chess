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
    URI uri = new URI("ws://0.0.0.0:8080/connect");
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    this.session = container.connectToServer(this, uri);

    this.session.addMessageHandler((MessageHandler.Whole<String>) message -> {
      ServerMessage serverResponse = serializer.fromJSON(message, ServerMessage.class);
      System.out.println(serverResponse.toString());
      msgObserver.notify(serverResponse);
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
