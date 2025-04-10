package websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  private ConcurrentHashMap<Integer, Collection<Connection>> connections = new ConcurrentHashMap<>();

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

  public Connection getConnection (String username, Integer gameID){
    ArrayList<Connection> listToSearch = (ArrayList<Connection>) connections.get(gameID);
    for (Connection conn : listToSearch){
      if (conn.getUsername().equals(username)){
        return conn;
      }
    }
    return null;
  }

  public void removeConnectionFromGame(Integer gameID, Connection connection){
    ArrayList<Connection> listToEdit =(ArrayList<Connection>) connections.get(gameID);
    int removalIndex = listToEdit.indexOf(connection);
    if (removalIndex == -1){return;}
    listToEdit.remove(removalIndex);
    connections.put(gameID, listToEdit);
  }

  public void broadcast(String excludeUsername, String notification, Integer gameID ) throws IOException {
    ArrayList<Connection> gameToNotify = (ArrayList<Connection>) connections.get(gameID);
    ArrayList<Connection> connectionsToRemove = new ArrayList<>();
    for (Connection conn : gameToNotify){
      if (conn.getSession().isOpen()){
        if(!conn.getUsername().equals(excludeUsername)){
          conn.send(notification);
        }
      } else{
        connectionsToRemove.add(conn);
      }
    }

    for (Connection conn : connectionsToRemove){
      this.removeConnectionFromGame(gameID, conn);
    }
  }

}
