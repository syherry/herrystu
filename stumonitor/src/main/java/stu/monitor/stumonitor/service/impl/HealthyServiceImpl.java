package stu.monitor.stumonitor.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stu.monitor.stumonitor.dao.HealthyDao;
import stu.monitor.stumonitor.pojo.Healthy;
import stu.monitor.stumonitor.service.HealthyService;

import javax.annotation.Resource;
@Service
public class HealthyServiceImpl implements HealthyService {
    @Resource
    HealthyDao healthyDao;
    @Override
    public Page<Healthy> findAll(Pageable pageable) {
        return healthyDao.findAll(pageable);
    }

    @Override
    public Healthy findByStuName(String stuName) {
        return healthyDao.findByStuName(stuName);
    }

    @Override
    public void save(Healthy healthy) {
        healthyDao.save(healthy);
    }

    @Override
    public Page<Healthy> findHealthiesByClassNameLike(Pageable pageable, String className) {
        return healthyDao.findHealthiesByClassNameLike(pageable,className);
    }

    @Override
    public Page<Healthy> findHealthiesByClassNameAndStuNameLike(Pageable pageable, String className, String stuName) {
        return healthyDao.findHealthiesByClassNameAndStuNameLike(pageable,className,stuName);
    }
}
