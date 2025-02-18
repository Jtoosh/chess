package dataaccess;

import model.UserData;

public interface UserDAO {

  public UserData getUserData(String username);
  public void createUser(String username, String password, String email);
  public void clearUserData();
}
