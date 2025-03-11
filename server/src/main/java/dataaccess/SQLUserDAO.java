package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.List;

public class SQLUserDAO implements UserDAO{
    @Override
    public UserData getUserData(String username) {
        return null;
    }

    @Override
    public void createUser(String username, String password, String email) {

    }

    @Override
    public void clearUserData() {

    }

    @Override
    public Collection<UserData> getUserList() {
        return List.of();
    }
}
