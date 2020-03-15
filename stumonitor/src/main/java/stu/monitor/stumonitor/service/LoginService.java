package stu.monitor.stumonitor.service;


import stu.monitor.stumonitor.pojo.User;


public interface LoginService {
     User findUserAndPasswdAndRole(String username,String password,String role);
     void saveUser(User user);
     User findUserAndRole(String username,String role);
}
