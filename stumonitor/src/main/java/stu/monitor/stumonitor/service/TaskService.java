package stu.monitor.stumonitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stu.monitor.stumonitor.pojo.Tasks;

public interface TaskService {
    Tasks findByStuName(String stuName);
    Page<Tasks> findTasksByClassName(String className, Pageable pageable);
    void save(Tasks tasks);
    Page<Tasks> findTasksByClassNameAndStuNameLike(String className, String stuName,Pageable pageable);
    Page<Tasks> findTasksByClassNameAndRate(String className,String rate, Pageable pageable);
    Page<Tasks> findTasksByClassNameAndRateAndStuNameLike(String className,String rate, String stuName,Pageable pageable);
}
