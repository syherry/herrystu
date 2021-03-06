package stu.monitor.stumonitor.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import stu.monitor.stumonitor.pojo.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsernameAndPasswordAndRole(String username,String password,String role);
    User findByUsernameAndRole(String username,String role);
    User findByUsername(String username);
    Page<User> findUsersByRole(String role,Pageable pageable);
    Page<User> findUsersByRoleAndUsernameLike(String role,String username,Pageable pageable);
}
