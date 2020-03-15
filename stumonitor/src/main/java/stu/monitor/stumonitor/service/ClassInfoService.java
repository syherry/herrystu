package stu.monitor.stumonitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stu.monitor.stumonitor.pojo.ClassInfo;

public interface ClassInfoService {
    Page<ClassInfo> findClassInfosByClassName(String className, Pageable pageable);
    void saveClassInfo(ClassInfo classInfo);
    Page<ClassInfo> findall( Pageable pageable);
    ClassInfo findByClassNameAndCollegeAndDepartment(String classname,String college,String department);
}
