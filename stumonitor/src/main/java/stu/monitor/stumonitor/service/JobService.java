package stu.monitor.stumonitor.service;

import stu.monitor.stumonitor.pojo.Job;

import java.util.List;

public interface JobService {
    public List<Job> jobFindAll();
    public void jobSave(Job job);
}
