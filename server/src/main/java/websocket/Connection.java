package websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
  private String username;
  private Session session;

  public Connection(String usernameParam, Session sessionParam){
    this.username = usernameParam;
    this.session = sessionParam;
  }
  public String getUsername(){return this.username;}

  public void send(String msg) throws IOException {
    this.session.getRemote().sendString(msg);
  }
}
