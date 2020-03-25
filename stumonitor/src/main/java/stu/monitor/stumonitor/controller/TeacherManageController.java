package stu.monitor.stumonitor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import stu.monitor.stumonitor.dao.ClassInfoDao;
import stu.monitor.stumonitor.pojo.ClassInfo;
import stu.monitor.stumonitor.pojo.Job;
import stu.monitor.stumonitor.pojo.MyErrors;
import stu.monitor.stumonitor.pojo.Teacher;
import stu.monitor.stumonitor.service.ClassInfoService;
import stu.monitor.stumonitor.service.JobService;
import stu.monitor.stumonitor.service.LoginService;
import stu.monitor.stumonitor.service.TeacherService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class TeacherManageController {
    @Resource
    private TeacherService teacherService;
    @Resource
    private LoginService loginService;
    @Resource
    private ClassInfoService classInfoService;

    @Resource
    private JobService jobService;
    @RequestMapping("/api/teacher/findByUserName")
    @ResponseBody
    public JSON findByUserName(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        if(username.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }
        Teacher teacher = teacherService.findByUserName(username);
        if (teacher == null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }
        return null;
    }
    @RequestMapping("/api/teacher/showspace")
    @ResponseBody
    public JSON showspace(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        if(username.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }
        Teacher teacher = teacherService.findByUserName(username);
        if (teacher == null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }
        if(teacher.getTeacherName() == null || teacher.getTeacherName().equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }
        if(teacher.getClassName() == null || teacher.getClassName().equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }
        List<Job> jobList = jobService.findByClassName(teacher.getClassName());
        return (JSON) JSONObject.parse(JSON.toJSONString(jobList));
    }
    @RequestMapping("/api/teacher/uploadClass")
    @ResponseBody
    public JSON uploadClass(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        String jobName = params.get("jobName") == null ? "" : params.get("jobName").toString();
        String className = params.get("className") == null ? "" : params.get("className").toString();
        String teaName = params.get("teaName") == null ? "" : params.get("teaName").toString();
        if (username.equals("") || loginService.findbyUsernam(username) == null){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }else if(teacherService.findByUserName(username)!=null){
            return (JSONObject) JSON.toJSON(new MyErrors("exist"));
        }
        if (jobName.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入职称"));
        }
        if (className.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入班级名称"));
        }
        if (teaName.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入您的姓名"));
        }
        ClassInfo classInfo = classInfoService.findByClassName(className);
        if ( classInfo == null){
            return (JSONObject) JSON.toJSON(new MyErrors("输入的班级不存在"));
        }
        Teacher teacher = new Teacher();
        teacher.setUserName(username);
        teacher.setClassName(className);
        classInfo.setClassName(className);
        teacher.setTeacherName(teaName);
        classInfo.setInstructor(teaName);
        teacher.setTitle(jobName);
        classInfoService.saveClassInfo(classInfo);
        teacherService.save(teacher);
        return (JSONObject) JSON.toJSON(teacher);
    }
}
