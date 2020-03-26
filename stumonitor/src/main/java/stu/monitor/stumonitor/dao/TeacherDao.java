package stu.monitor.stumonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.monitor.stumonitor.pojo.Teacher;

public interface TeacherDao extends JpaRepository<Teacher,Long> {

    Teacher findByUserName(String username);
    Teacher findByTeacherName(String teaName);
}
