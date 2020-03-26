package stu.monitor.stumonitor.service;

import stu.monitor.stumonitor.pojo.Teacher;

public interface TeacherService {
    Teacher findByUserName(String username);
    void save(Teacher teacher);
    Teacher findByTeacherName(String teaName);

}
