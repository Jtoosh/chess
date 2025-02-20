package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  private ArrayList<AuthData> authDataList = new ArrayList<>();

  @Override
  public AuthData getAuthData(String username) {
    for (AuthData authData : this.authDataList){
      if (authData.username().equals(username)){
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
  public Collection<AuthData> getAuthDataList() {
    return this.authDataList;
  }

  @Override
  public void clearAuthData() {
    if (this.authDataList != null){
      this.authDataList.clear();
    }
  }
}
