package dataaccess;

import model.AuthData;

import java.util.Collection;
import java.util.List;

public class SQLAuthDAO implements AuthDAO{
    @Override
    public AuthData getAuthData(String username) {
        return null;
    }

    @Override
    public AuthData createAuth(String username) {
        return null;
    }

    @Override
    public Collection<AuthData> getAuthDataList() {
        return List.of();
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void clearAuthData() {

    }
}
