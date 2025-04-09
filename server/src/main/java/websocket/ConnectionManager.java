package websocket;


import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public class ConnectionManager {
  private TreeMap<Integer, Collection<Connection>> connections = new TreeMap<>();

  public void add(Integer gameID, Connection userConnection){
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

  public void removeConnectionFromGame(Integer gameID, Connection connection){
    ArrayList<Connection> listToEdit =(ArrayList<Connection>) connections.get(gameID);
    int removalIndex = listToEdit.indexOf(connection);
    listToEdit.remove(removalIndex);
    connections.put(gameID, listToEdit);
  }

  public void broadcast(String excludeUsername, String notification, Integer gameID ) throws IOException {
    ArrayList<Connection> gameToNotify = (ArrayList<Connection>) connections.get(gameID);
    for (Connection conn : gameToNotify){
      if (conn.getSession().isOpen()){
        if(!conn.getUsername().equals(excludeUsername)){
          conn.send(notification);
        }
      } else{
        this.removeConnectionFromGame(gameID, conn);
      }
    }
  }

}
