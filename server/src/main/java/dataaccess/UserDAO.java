package dataaccess;

import model.UserData;

import java.util.Collection;

public interface UserDAO {

  public UserData getUserData(String username);
  public void createUser(String username, String password, String email);
  public void clearUserData();
  public Collection<UserData> getUserList();
}
