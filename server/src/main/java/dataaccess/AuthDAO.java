package dataaccess;

import model.AuthData;

import java.util.Collection;

public interface AuthDAO {
  AuthData getAuthData(String Username);

  AuthData createAuth(String username);

  Collection<AuthData> getAuthDataList();

  void deleteAuth(String authToken);

  void clearAuthData();
}
