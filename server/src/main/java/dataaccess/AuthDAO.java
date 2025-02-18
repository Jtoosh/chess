package dataaccess;

import model.AuthData;

public interface AuthDAO {
  public AuthData getAuthData(String Username);

  void createAuth(String username);

  public void clearAuthData();
}
