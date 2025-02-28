package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
  private final ArrayList<UserData> userData = new ArrayList<>();

  @Override
  public UserData getUserData(String username) {
    for (UserData user : userData){
      if (user.username().equals(username)){
        return user;
      }
    }
    return null;
  }

  @Override
  public void createUser(String username, String password, String email) {
    this.userData.add(new UserData(username,password,email));
  }

  @Override
  public void clearUserData() {
    if (this.userData != null){
      this.userData.clear();
    }
  }

  @Override
  public Collection<UserData> getUserList() {
    return this.userData;
  }
}
