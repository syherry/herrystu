package stu.monitor.stumonitor.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import stu.monitor.stumonitor.pojo.Tasks;

public interface TasksDao extends JpaRepository<Tasks,Long> {
    Tasks findByStuName(String stuName);
    Page<Tasks> findTasksByClassNameLike(String className, Pageable pageable);
    Page<Tasks> findTasksByClassNameAndStuNameLike(String className, String stuName,Pageable pageable);
    Page<Tasks> findTasksByClassNameAndRate(String className,String rate, Pageable pageable);
    Page<Tasks> findTasksByClassNameAndRateAndStuNameLike(String className,String rate, String stuName,Pageable pageable);
}
