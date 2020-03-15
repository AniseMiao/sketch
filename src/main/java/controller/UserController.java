package controller;

import PO.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController
@RequestMapping("sketch/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public void addUser(String username, String password, String nickname, String phone){
        userService.addUser(username, password, nickname, phone);
    }

    @PostMapping("/deleteUser")
    public void deleteUser(String aid){
        userService.deleteUser(aid);
    }

    @PostMapping("/updateUser")
    public void updateUser(String aid, String username, String password, String nickname, String phone){
        userService.updateUser(aid, username, password, nickname, phone);
    }

    @PostMapping("/getUser")
    public UserPO getUser(String aid){
        return userService.getUser(aid);
    }

    @PostMapping("/login")
    public boolean login(String username, String password){
        return userService.login(username, password);
    }
}
