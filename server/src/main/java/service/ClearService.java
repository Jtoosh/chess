package service;

public class ClearService extends ParentService{

  public void clear(){

    super.clearUserData();
    super.clearAuthData();
    super.clearGameData();
  }
}
