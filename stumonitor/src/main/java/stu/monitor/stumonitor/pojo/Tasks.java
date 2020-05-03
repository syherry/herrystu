package stu.monitor.stumonitor.pojo;

import javax.persistence.*;

@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String stuName;
    @Column(nullable = false)
    private String className;
    @Column(nullable = false)
    private String tasksInfo;
    @Column(nullable = false)
    private String rate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTasksInfo() {
        return tasksInfo;
    }

    public void setTasksInfo(String tasksInfo) {
        this.tasksInfo = tasksInfo;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
