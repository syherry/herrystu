package stu.monitor.stumonitor.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import stu.monitor.stumonitor.pojo.Job;

import java.util.List;

public interface JobDao extends JpaRepository<Job,Long> {

    List<Job> findAll();
    Job findByUserName(String username);
    List<Job> findByClassName(String classname);
    Page<Job> findJobsByClassNameAndStuNameLike(String classname,String stuname,Pageable pageable);
    Page<Job> findJobsByClassNameLike(String classname,Pageable pageable);
}
