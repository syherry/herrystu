package stu.monitor.stumonitor.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import stu.monitor.stumonitor.pojo.ClassInfo;

public interface ClassInfoDao extends JpaRepository<ClassInfo, Long> {
    Page<ClassInfo> findClassInfosByClassNameLike(String className, Pageable pageable);
    Page<ClassInfo> findAll(Pageable pageable);
    ClassInfo findByClassNameAndCollegeAndDepartment(String classname,String college,String department);
}
