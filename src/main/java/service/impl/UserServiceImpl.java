package service.impl;

import PO.UserPO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import dao.UserDao;
import service.UserService;
import util.IdUtil;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public boolean addUser(String username, String password, String nickname, String phone){
        UserPO user = new UserPO();
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
        UserPO user = new UserPO();
        user.setAid(aid);
        user.setAid(aid);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setPhone(phone);
        userDao.updateUser(user);
    }

    @PostMapping("/getUser")
    public UserPO getUser(String username){
        return userDao.getUser(username);
    }

    @PostMapping("/loginUser")
    public boolean login(String username, String password){
        return userDao.login(username, password);
    }
}
