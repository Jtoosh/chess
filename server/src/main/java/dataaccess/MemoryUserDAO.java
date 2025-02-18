package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO{
  private ArrayList<UserData> userData;

  @Override
  public UserData getUserData(String username) {
    for (UserData user : userData){
      if (user.username() == username){
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
    this.userData.clear();
  }
}
