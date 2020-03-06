package stu.monitor.stumonitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.monitor.stumonitor.pojo.Job;

import java.util.List;

public interface JobDao extends JpaRepository<Job,Long> {

    List<Job> findAll();

}
