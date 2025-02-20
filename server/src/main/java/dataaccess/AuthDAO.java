package dataaccess;

import model.AuthData;

import java.util.Collection;

public interface AuthDAO {
  public AuthData getAuthData(String Username);

  public void createAuth(String username);

  public Collection<AuthData> getAuthDataList();

  public void clearAuthData();
}
