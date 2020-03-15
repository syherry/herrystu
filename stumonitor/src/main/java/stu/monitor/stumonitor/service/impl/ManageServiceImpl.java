package stu.monitor.stumonitor.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stu.monitor.stumonitor.dao.UserDao;
import stu.monitor.stumonitor.pojo.User;
import stu.monitor.stumonitor.service.ManageService;

import javax.annotation.Resource;

@Service
public class ManageServiceImpl implements ManageService {

    @Resource
    UserDao userDao;

    @Override
    public Page<User> findUsersByRole(String role, Pageable pageable) {
        return userDao.findUsersByRole(role,pageable);
    }

    @Override
    public Page<User> findUserByRoleAndUsernameLike(String role, String username, Pageable pageable) {
        return userDao.findUsersByRoleAndUsernameLike(role, username, pageable);
    }
}
