package dao;

import PO.UserPO;

public interface UserDao {
    public void addUser(UserPO user);

    public void deleteUser(String aid);

    public void updateUser(UserPO user);

    public UserPO getUser(String aid);

    public boolean login(String username, String password);
}
