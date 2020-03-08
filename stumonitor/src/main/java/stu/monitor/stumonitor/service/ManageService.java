package stu.monitor.stumonitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stu.monitor.stumonitor.pojo.User;

public interface ManageService {
    Page<User> findUsersByRole(String role, Pageable pageable);
}
