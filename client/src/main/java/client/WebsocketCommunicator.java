package client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class WebsocketCommunicator {
  private final ServerMessageObserver msgObserver;
  private Map<Integer, Collection<String>> activeClients = new TreeMap<>();

  public WebsocketCommunicator(ServerMessageObserver msgObserver){
    this.msgObserver = msgObserver;
  }
}
