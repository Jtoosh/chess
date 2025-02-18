package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  private ArrayList<AuthData> authDataList;

  @Override
  public AuthData getAuthData(String username) {
    for (AuthData authData : this.authDataList){
      if (authData.username() == username){
        return authData;
      }
    }
    return null;
  }

  @Override
  public void createAuth(String username) {
    this.authDataList.add(new AuthData(username, UUID.randomUUID().toString()));
  }

  @Override
  public void clearAuthData() {
  this.authDataList.clear();
  }
}
