package stu.monitor.stumonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.monitor.stumonitor.pojo.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsernameAndPasswordAndRole(String username,String password,String role);
    User findByUsernameAndRole(String username,String role);
}
