package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  private final ArrayList<AuthData> authDataList = new ArrayList<>();

  @Override
  public AuthData getAuthData(String stringToFind) {
    for (AuthData authData : this.authDataList){
      if (authData.username().equals(stringToFind) || authData.authToken().equals(stringToFind)){
        return authData;
      }
    }
    return null;
  }

  @Override
  public AuthData createAuth(String username) {
    AuthData newAuthData = new AuthData(UUID.randomUUID().toString(), username);
    this.authDataList.add(newAuthData);
    return newAuthData;
  }

  @Override
  public Collection<AuthData> getAuthDataList() {
    return this.authDataList;
  }

  @Override
  public void deleteAuth(String authTokenParameter) {
    this.authDataList.removeIf(authData -> authData.authToken().equals(authTokenParameter));
  }

  @Override
  public void clearAuthData() {
    this.authDataList.clear();

  }
}
