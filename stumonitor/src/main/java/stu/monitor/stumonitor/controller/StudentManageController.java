package stu.monitor.stumonitor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import stu.monitor.stumonitor.pojo.Job;
import stu.monitor.stumonitor.pojo.MyErrors;
import stu.monitor.stumonitor.pojo.Tasks;
import stu.monitor.stumonitor.pojo.Teacher;
import stu.monitor.stumonitor.service.ClassInfoService;
import stu.monitor.stumonitor.service.JobService;
import stu.monitor.stumonitor.service.LoginService;
import stu.monitor.stumonitor.service.TaskService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin
public class StudentManageController {
    @Resource
    private JobService jobService;
    @Resource
    private ClassInfoService classInfoService;
    @Resource
    private LoginService loginService;
    @Resource
    private TaskService taskService;
    @RequestMapping("/api/student/uploadJob")
    @ResponseBody
    public JSON uploadJob(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        String company = params.get("company") == null ? "" : params.get("company").toString();
        String jobName = params.get("jobName") == null ? "" : params.get("jobName").toString();
        String className = params.get("className") == null ? "" : params.get("className").toString();
        String stuName = params.get("stuName") == null ? "" : params.get("stuName").toString();
        if (username.equals("") || loginService.findbyUsernam(username) == null){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }else if(jobService.findJobByUserName(username)!=null){
            return (JSONObject) JSON.toJSON(new MyErrors("exist"));
        }
        if (company.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入公司名称"));
        }
        if (jobName.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入职位"));
        }
        if (className.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入班级名称"));
        }
        if (stuName.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入您的姓名"));
        }
        if (classInfoService.findByClassName(className) == null){
            return (JSONObject) JSON.toJSON(new MyErrors("输入的班级不存在"));
        }
        Job job = new Job();
        job.setClassName(className);
        job.setCompany(company);
        job.setJobName(jobName);
        job.setStuName(stuName);
        job.setUserName(username);
        jobService.jobSave(job);
        return (JSONObject) JSON.toJSON(job);
    }
    @RequestMapping("/api/student/findByUserName")
    @ResponseBody
    public JSON findByUserName(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        if(username.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }
        Job job = jobService.findJobByUserName(username);
        if (job == null){
            return (JSONObject) JSON.toJSON(new MyErrors("当前帐号未绑定班级与工作信息！"));
        }
        return (JSON) JSON.toJSON(job);
    }
    @RequestMapping("/api/student/uploadspace")
    @ResponseBody
    public JSON uploadspace(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        String address = params.get("address") == null ? "" : params.get("address").toString();
        String coordinate = params.get("coordinate") == null ? "" : params.get("coordinate").toString();
        if(username.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }
        Job job = jobService.findJobByUserName(username);
        if (job == null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }
        job.setAddress(address);
        job.setCoordinate(coordinate);
        jobService.jobSave(job);
        return (JSON) JSON.toJSON(job);
    }
    @RequestMapping("/api/student/findJob")
    @ResponseBody
    public JSON findJob(@RequestParam Map<String,Object> params) {
        String username = params.get("username") == null ? "" : params.get("username").toString();
        Job job = jobService.findJobByUserName(username);
        if (username.equals("") || loginService.findbyUsernam(username) == null){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }else if (job == null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }
        return (JSON) JSON.toJSON(job);
    }
    @RequestMapping("/api/student/updateJob")
    @ResponseBody
    public JSON updateJob(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        String company = params.get("company") == null ? "" : params.get("company").toString();
        String jobName = params.get("jobName") == null ? "" : params.get("jobName").toString();
        String className = params.get("className") == null ? "" : params.get("className").toString();
        String stuName = params.get("stuName") == null ? "" : params.get("stuName").toString();
        Job job = jobService.findJobByUserName(username);
        if (username.equals("") || loginService.findbyUsernam(username) == null){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }else if(job==null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }
        if (company.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入公司名称"));
        }
        if (jobName.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入职位"));
        }
        if (className.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入班级名称"));
        }
        if (stuName.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入您的姓名"));
        }
        if (classInfoService.findByClassName(className) == null){
            return (JSONObject) JSON.toJSON(new MyErrors("输入的班级不存在"));
        }

        job.setClassName(className);
        job.setCompany(company);
        job.setJobName(jobName);
        job.setStuName(stuName);
        job.setUserName(username);
        jobService.jobSave(job);
        return (JSONObject) JSON.toJSON(job);
    }
    @RequestMapping("/api/student/findTasks")
    @ResponseBody
    public JSON findTasks(@RequestParam Map<String,Object> params){

        String stuname = params.get("stuname") == null ? "" : params.get("stuname").toString();
        JSONObject jsonObject;
        if (stuname.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }
        Tasks tasks = taskService.findByStuName(stuname);
        if (tasks == null){
            return (JSONObject) JSON.toJSON(new MyErrors("未发布任务!"));
        }
        jsonObject = (JSONObject) JSON.toJSON(tasks);
        return jsonObject;
    }
    @RequestMapping("/api/student/updateTasks")
    @ResponseBody
    public JSON updateTasks(@RequestParam Map<String,Object> params){

        String stuname = params.get("stuname") == null ? "" : params.get("stuname").toString();
        JSONObject jsonObject;
        if (stuname.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }
        Tasks tasks = taskService.findByStuName(stuname);
        if (tasks == null){
            return (JSONObject) JSON.toJSON(new MyErrors("未发布任务!"));
        }
        if (!"未接收".equals(tasks.getRate())){
            return (JSONObject) JSON.toJSON(new MyErrors("只有未接收的任务才可以接收！"));
        }
        tasks.setRate("已接收");
        taskService.save(tasks);
        jsonObject = (JSONObject) JSON.toJSON(tasks);
        return jsonObject;
    }
    @RequestMapping("/api/student/finalTasks")
    @ResponseBody
    public JSON finalTasks(@RequestParam Map<String,Object> params){

        String stuname = params.get("stuname") == null ? "" : params.get("stuname").toString();
        JSONObject jsonObject;
        if (stuname.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }
        Tasks tasks = taskService.findByStuName(stuname);
        if (tasks == null){
            return (JSONObject) JSON.toJSON(new MyErrors("未发布任务!"));
        }
        if (!"已接收".equals(tasks.getRate())){
            return (JSONObject) JSON.toJSON(new MyErrors("任务状态未接收或已完成"));
        }
        tasks.setRate("已完成");
        taskService.save(tasks);
        jsonObject = (JSONObject) JSON.toJSON(tasks);
        return jsonObject;
    }
}
