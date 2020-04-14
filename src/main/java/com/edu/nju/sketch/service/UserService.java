package com.edu.nju.sketch.service;

import com.edu.nju.sketch.PO.User;

public interface UserService {

    public boolean addUser(String username, String password, String nickname, String phone);

    public void deleteUser(String aid);

    public void updateUser(String aid, String username, String password, String nickname, String phone);

    public User getUser(String username);

    public boolean login(String username, String password);
}
