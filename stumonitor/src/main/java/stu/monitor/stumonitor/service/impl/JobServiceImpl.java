package stu.monitor.stumonitor.service.impl;

import org.springframework.stereotype.Service;
import stu.monitor.stumonitor.dao.JobDao;
import stu.monitor.stumonitor.pojo.Job;
import stu.monitor.stumonitor.service.JobService;

import javax.annotation.Resource;
import java.util.List;
@Service
public class JobServiceImpl implements JobService {
    @Resource
    public JobDao jobDao;
    @Override
    public List<Job> jobFindAll() {
        return jobDao.findAll();
    }

    @Override
    public void jobSave(Job job) {
        jobDao.save(job);
    }

    @Override
    public Job findJobByUserName(String username) {
        return jobDao.findByUserName(username);
    }

    @Override
    public List<Job> findByClassName(String classname) {
        return jobDao.findByClassName(classname);
    }
}
