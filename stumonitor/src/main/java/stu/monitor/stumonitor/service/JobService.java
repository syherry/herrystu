package stu.monitor.stumonitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stu.monitor.stumonitor.pojo.Job;

import java.util.List;

public interface JobService {
    List<Job> jobFindAll();
    void jobSave(Job job);
    Job findJobByUserName(String username);
    List<Job> findByClassName(String classname);
    Page<Job> findJobsByClassNameAndStuNameLike(String classname, String stuname, Pageable pageable);
    Page<Job> findJobsByClassNameLike(String classname,Pageable pageable);
}
