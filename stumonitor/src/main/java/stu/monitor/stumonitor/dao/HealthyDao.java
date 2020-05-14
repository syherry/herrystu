package stu.monitor.stumonitor.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import stu.monitor.stumonitor.pojo.Healthy;

public interface HealthyDao extends JpaRepository<Healthy, Long> {

    Page<Healthy> findAll(Pageable pageable);
    Healthy findByStuName(String stuName);
    Page<Healthy> findHealthiesByClassNameLike(Pageable pageable,String className);
    Page<Healthy> findHealthiesByClassNameAndStuNameLike(Pageable pageable,String className,String stuName);
}
