package stu.monitor.stumonitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stu.monitor.stumonitor.pojo.Healthy;


public interface HealthyService {
    Page<Healthy> findAll(Pageable pageable);
    Healthy findByStuName(String stuName);
    void save(Healthy healthy);
    Page<Healthy> findHealthiesByClassNameLike(Pageable pageable,String className);
    Page<Healthy> findHealthiesByClassNameAndStuNameLike(Pageable pageable,String className,String stuName);
}
