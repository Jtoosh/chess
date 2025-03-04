package dataaccess;

import model.UserData;

import java.util.Collection;

public interface UserDAO {

  UserData getUserData(String username);
  void createUser(String username, String password, String email);
  void clearUserData();
  Collection<UserData> getUserList();
}
