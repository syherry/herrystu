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
import stu.monitor.stumonitor.service.TeacherService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class TeacherManageController {
    @Resource
    private TeacherService teacherService;

    @Resource JobService jobService;
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
}
