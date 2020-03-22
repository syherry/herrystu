package stu.monitor.stumonitor.service.impl;

import org.springframework.stereotype.Service;
import stu.monitor.stumonitor.dao.UserDao;
import stu.monitor.stumonitor.pojo.User;
import stu.monitor.stumonitor.service.LoginService;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserDao userDao;


    @Override
    public User findUserAndPasswdAndRole(String username, String password, String role) {
        return userDao.findByUsernameAndPasswordAndRole(username,password,role);
    }


    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public User findUserAndRole(String username, String role) {
        return  userDao.findByUsernameAndRole(username,role);
    }

    @Override
    public User findbyUsernam(String username) {
        return userDao.findByUsername(username);
    }
}
