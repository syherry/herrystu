package stu.monitor.stumonitor.service;


import stu.monitor.stumonitor.pojo.User;


public interface LoginService {
    public User findUserAndPasswdAndRole(String username,String password,String role);
    public void saveUser(User user);
    public User findUserAndRole(String username,String role);
}
