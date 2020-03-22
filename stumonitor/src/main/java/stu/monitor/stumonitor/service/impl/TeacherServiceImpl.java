package stu.monitor.stumonitor.service.impl;

import org.springframework.stereotype.Service;
import stu.monitor.stumonitor.dao.TeacherDao;
import stu.monitor.stumonitor.pojo.Teacher;
import stu.monitor.stumonitor.service.TeacherService;

import javax.annotation.Resource;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    TeacherDao teacherDao;
    @Override
    public Teacher findByUserName(String username) {
        return teacherDao.findByUserName(username);
    }

    @Override
    public void save(Teacher teacher) {
        teacherDao.save(teacher);
    }
}
