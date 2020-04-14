package com.edu.nju.sketch.service.impl;

import com.edu.nju.sketch.PO.User;
import com.edu.nju.sketch.dao.UserDao;
import com.edu.nju.sketch.dao.impl.UserDaoImpl;
import com.edu.nju.sketch.service.UserService;
import com.edu.nju.sketch.util.IdUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    public boolean addUser(String username, String password, String nickname, String phone){
        User user = new User();
        String aid = IdUtil.getAccountId();
        user.setAid(aid);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setPhone(phone);
        return userDao.addUser(user);
    }

    @PostMapping("/deleteUser")
    public void deleteUser(String aid){
        userDao.deleteUser(aid);
    }

    public void updateUser(String aid, String username, String password, String nickname, String phone){
        User user = new User();
        user.setAid(aid);
        user.setAid(aid);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setPhone(phone);
        userDao.updateUser(user);
    }

    @PostMapping("/getUser")
    public User getUser(String username){
        return userDao.getUser(username);
    }

    @PostMapping("/loginUser")
    public boolean login(String username, String password){
        return userDao.login(username, password);
    }
}
