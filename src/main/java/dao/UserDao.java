package dao;

import PO.UserPO;

public interface UserDao {
    public boolean addUser(UserPO user);

    public void deleteUser(String aid);

    public void updateUser(UserPO user);

    public UserPO getUser(String username);

    public boolean login(String username, String password);
}
