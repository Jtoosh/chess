package websocket;


import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public class ConnectionManager {
  private TreeMap<Integer, Collection<Connection>> connections = new TreeMap<>();

  public void add(Integer gameID, String username, Session session){
    Connection userConnection = new Connection(username, session);
    ArrayList<Connection> gameClients = new ArrayList<>();
    if (connections.containsKey(gameID)){
      gameClients = (ArrayList<Connection>) connections.get(gameID);
    }
    gameClients.add(userConnection);
    connections.put(gameID,gameClients);
  }

  public void remove(Integer gameID){
    connections.remove(gameID);
  }

  public void broadcast

}
