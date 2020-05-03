package stu.monitor.stumonitor.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stu.monitor.stumonitor.dao.TasksDao;
import stu.monitor.stumonitor.pojo.Tasks;
import stu.monitor.stumonitor.service.TaskService;

import javax.annotation.Resource;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    TasksDao tasksDao;

    @Override
    public Tasks findByStuName(String stuName) {
        return tasksDao.findByStuName(stuName);
    }

    @Override
    public Page<Tasks> findTasksByClassName(String className, Pageable pageable) {
        return tasksDao.findTasksByClassNameLike(className,pageable);
    }

    @Override
    public void save(Tasks tasks) {
        tasksDao.save(tasks);
    }
    public Page<Tasks> findTasksByClassNameAndStuNameLike(String className, String stuName, Pageable pageable){
        return tasksDao.findTasksByClassNameAndStuNameLike(className,stuName,pageable);
    }

    @Override
    public Page<Tasks> findTasksByClassNameAndRate(String className, String rate, Pageable pageable) {
        return tasksDao.findTasksByClassNameAndRate(className,rate,pageable);
    }

    @Override
    public Page<Tasks> findTasksByClassNameAndRateAndStuNameLike(String className, String rate, String stuName, Pageable pageable) {
        return tasksDao.findTasksByClassNameAndRateAndStuNameLike(className,rate,stuName,pageable);
    }
}
