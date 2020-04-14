package com.edu.nju.sketch.dao;


import com.edu.nju.sketch.PO.User;

public interface UserDao {
    public boolean addUser(User user);

    public void deleteUser(String aid);

    public void updateUser(User user);

    public User getUser(String username);

    public boolean login(String username, String password);
}
