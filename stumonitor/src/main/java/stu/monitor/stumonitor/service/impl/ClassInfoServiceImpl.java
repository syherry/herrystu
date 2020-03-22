package stu.monitor.stumonitor.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stu.monitor.stumonitor.dao.ClassInfoDao;
import stu.monitor.stumonitor.pojo.ClassInfo;
import stu.monitor.stumonitor.service.ClassInfoService;

import javax.annotation.Resource;

@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Resource
    ClassInfoDao classInfoDao;
    @Override
    public Page<ClassInfo> findClassInfosByClassName(String className, Pageable pageable) {
        return classInfoDao.findClassInfosByClassNameLike(className,pageable);
    }

    @Override
    public void saveClassInfo(ClassInfo classInfo) {
        classInfoDao.save(classInfo);
    }

    @Override
    public Page<ClassInfo> findall(Pageable pageable) {
        return classInfoDao.findAll(pageable);
    }

    @Override
    public ClassInfo findByClassNameAndCollegeAndDepartment(String classname, String college, String department) {
        return classInfoDao.findByClassNameAndCollegeAndDepartment(classname,college,department);
    }

    @Override
    public ClassInfo findByClassName(String classname) {
        return classInfoDao.findByClassName(classname);
    }

    @Override
    public ClassInfo findByInstructor(String instructor) {
        return classInfoDao.findByInstructor(instructor);
    }
}
