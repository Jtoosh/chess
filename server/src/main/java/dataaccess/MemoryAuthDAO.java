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
  public void createAuth(String username) {
    this.authDataList.add(new AuthData(UUID.randomUUID().toString(), username));
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
  public boolean clearAuthData() {
    if (!this.authDataList.isEmpty()){
      this.authDataList.clear();
      return true;
    } else {return false;}
  }
}
