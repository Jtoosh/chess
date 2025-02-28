package dataaccess;

import model.AuthData;

import java.util.Collection;

public interface AuthDAO {
  AuthData getAuthData(String Username);

  void createAuth(String username);

  Collection<AuthData> getAuthDataList();

  void deleteAuth(String authToken);

  boolean clearAuthData();
}
