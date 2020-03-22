package stu.monitor.stumonitor.service;

import stu.monitor.stumonitor.pojo.Job;

import java.util.List;

public interface JobService {
    List<Job> jobFindAll();
    void jobSave(Job job);
    Job findJobByUserName(String username);
    List<Job> findByClassName(String classname);
}
