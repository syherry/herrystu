package stu.monitor.stumonitor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import stu.monitor.stumonitor.dao.ClassInfoDao;
import stu.monitor.stumonitor.pojo.*;
import stu.monitor.stumonitor.service.*;

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
    private TaskService taskService;

    @Resource
    private JobService jobService;
    @Resource
    private HealthyService healthyService;
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
        }else if (teacher.getClassName().equals("")||teacher.getClassName()==null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }else if (teacher.getTeacherName()==null||teacher.getTeacherName().equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("warn"));
        }
        return (JSON) JSON.toJSON(teacher);
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
            return (JSONObject) JSON.toJSON(new MyErrors("warn"));
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
        }else if(teacherService.findByUserName(username)!=null
                &&teacherService.findByUserName(username).getClassName()!=null
                &&!teacherService.findByUserName(username).getClassName().equals("")){
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
        Teacher bakteacher = teacherService.findByUserName(classInfo.getInstructor());
        if (bakteacher != null){
            bakteacher.setClassName("");
            teacherService.save(bakteacher);
        }
        Teacher teacher = teacherService.findByUserName(username);
        if (teacher == null){
            teacher = new Teacher();
        }
        teacher.setUserName(username);
        teacher.setClassName(className);
        teacher.setTeacherName(teaName);
        teacher.setTitle(jobName);
        classInfo.setInstructor(username);
        classInfoService.saveClassInfo(classInfo);
        teacherService.save(teacher);
        return (JSONObject) JSON.toJSON(teacher);
    }
    @RequestMapping("/api/teacher/updateClass")
    @ResponseBody
    public JSON updateClass(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        String jobName = params.get("jobName") == null ? "" : params.get("jobName").toString();
        String className = params.get("className") == null ? "" : params.get("className").toString();
        String teaName = params.get("teaName") == null ? "" : params.get("teaName").toString();
        Teacher lastTeacher = teacherService.findByUserName(username);
        if (username.equals("") || loginService.findbyUsernam(username) == null){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }else if(teacherService.findByUserName(username)==null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
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
        Teacher bakteacher = teacherService.findByUserName(classInfo.getInstructor());
        if (bakteacher != null){
            bakteacher.setClassName("");
            teacherService.save(bakteacher);
        }
        lastTeacher.setUserName(username);
        lastTeacher.setClassName(className);
        lastTeacher.setTeacherName(teaName);
        lastTeacher.setTitle(jobName);
        classInfo.setInstructor(username);
        classInfoService.saveClassInfo(classInfo);
        teacherService.save(lastTeacher);
        return (JSONObject) JSON.toJSON(lastTeacher);
    }
    @RequestMapping("/api/teacher/findStudent")
    @ResponseBody
    public JSON findStudent(@RequestParam Map<String,Object> params){
        int page = params.get("page") == null ? 0 : Integer.valueOf(params.get("page").toString());
        int size = params.get("size") == null ? 10 : Integer.valueOf(params.get("size").toString());
        String stuname = params.get("stuname") == null ? "" : params.get("stuname").toString();
        String teacheruser = params.get("teacheruser") == null ? "" : params.get("teacheruser").toString();
        JSONObject jsonObject;
        Pageable pageable = PageRequest.of(page,size);
        Teacher teacher = teacherService.findByUserName(teacheruser);
        if (teacher == null||teacher.getClassName().equals("")||teacher.getClassName() == null){
            return (JSONObject) JSON.toJSON(new MyErrors("查询出现异常，请联系管理员查看绑定班级信息"));
        }
        if (stuname.equals("")){
            Page<Job> userPage = jobService.findJobsByClassNameLike("%"+teacher.getClassName()+"%",pageable);
            jsonObject = (JSONObject) JSON.toJSON(userPage);
            return jsonObject;
        }else{
            stuname = "%" + stuname + "%";
        }
        Page<Job> userPage = jobService.findJobsByClassNameAndStuNameLike(teacher.getClassName(),stuname,pageable);
        jsonObject = (JSONObject) JSON.toJSON(userPage);
        return jsonObject;
    }
    @RequestMapping("/api/teacher/sendTask")
    @ResponseBody
    public JSON sendTask(@RequestParam Map<String,Object> params){
        String stuName = params.get("stuName") == null ? "" : params.get("stuName").toString();
        String teaName = params.get("teaName") == null ? "" : params.get("teaName").toString();
        String taskInfo = params.get("taskInfo") == null ? "" : params.get("taskInfo").toString();
        if (taskInfo==null||taskInfo==""){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入任务 "));
        }
        Teacher teacher = teacherService.findByUserName(teaName);
        if (teacher == null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }if(teacher.getClassName()==null||teacher.getClassName()==""){
            return (JSONObject) JSON.toJSON(new MyErrors("warn"));
        }
        JSONObject jsonObject;
        if (stuName.equals("")){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("选择的学生信息有误"));
            return jsonObject;
        }
        JSONArray list = JSONArray.parseArray(stuName);
        for (Object job:list){
            JSONObject thisrow = (JSONObject) JSONObject.toJSON(job);
            Tasks task = taskService.findByStuName(thisrow.getString("stuName"));
            if (task == null){
                task = new Tasks();
            }
            task.setRate("未接收");
            task.setStuName(thisrow.getString("stuName"));
            task.setClassName(teacher.getClassName());
            task.setTasksInfo(taskInfo);
            taskService.save(task);
        }

        return (JSON) JSON.toJSON(list);
    }
    @RequestMapping("/api/teacher/sendTaskAll")
    @ResponseBody
    public JSON sendTaskAll(@RequestParam Map<String,Object> params){
        String teaName = params.get("teaName") == null ? "" : params.get("teaName").toString();
        String tasksinfo = params.get("tasksinfo") == null ? "" : params.get("tasksinfo").toString();
        if (tasksinfo==null||tasksinfo==""){
            return (JSONObject) JSON.toJSON(new MyErrors("请输入任务 "));
        }
        Teacher teacher = teacherService.findByUserName(teaName);
        if (teacher == null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }if(teacher.getClassName()==null||teacher.getClassName()==""){
            return (JSONObject) JSON.toJSON(new MyErrors("warn"));
        }
        List<Job> list = jobService.findByClassName(teacher.getClassName());
        for(Job job:list){
            Tasks task = taskService.findByStuName(job.getUserName());
            if (task == null){
                task = new Tasks();
            }
            task.setRate("未接收");
            task.setStuName(job.getUserName());
            task.setClassName(teacher.getClassName());
            task.setTasksInfo(tasksinfo);
            taskService.save(task);
        }
        return (JSONObject) JSON.toJSON(new MyErrors("success"));
    }
    @RequestMapping("/api/teacher/findTasks")
    @ResponseBody
    public JSON findTasks(@RequestParam Map<String,Object> params){
        int page = params.get("page") == null ? 0 : Integer.valueOf(params.get("page").toString());
        int size = params.get("size") == null ? 10 : Integer.valueOf(params.get("size").toString());
        String stuname = params.get("stuname") == null ? "" : params.get("stuname").toString();
        String teacheruser = params.get("teacheruser") == null ? "" : params.get("teacheruser").toString();
        String rate = params.get("rate") == null ? "" : params.get("rate").toString();
        JSONObject jsonObject;
        Pageable pageable = PageRequest.of(page,size);
        Teacher teacher = teacherService.findByUserName(teacheruser);
        if (teacher == null||teacher.getClassName().equals("")||teacher.getClassName() == null){
            return (JSONObject) JSON.toJSON(new MyErrors("查询出现异常，请联系管理员查看绑定班级信息"));
        }
        switch (rate){
            case "all":
                if (stuname.equals("")){
                    Page<Tasks> userPage = taskService.findTasksByClassName("%"+teacher.getClassName()+"%",pageable);
                    jsonObject = (JSONObject) JSON.toJSON(userPage);
                    return jsonObject;
                }else{
                    stuname = "%" + stuname + "%";
                }
                Page<Tasks> userPage = taskService.findTasksByClassNameAndStuNameLike(teacher.getClassName(),stuname,pageable);
                jsonObject = (JSONObject) JSON.toJSON(userPage);
                return jsonObject;
            case "unreceive":
                if (stuname.equals("")){
                    Page<Tasks> unreceive = taskService.findTasksByClassNameAndRate(teacher.getClassName(),"未接收",pageable);
                    jsonObject = (JSONObject) JSON.toJSON(unreceive);
                    return jsonObject;
                }else{
                    stuname = "%" + stuname + "%";
                }
                Page<Tasks> unreceive = taskService.findTasksByClassNameAndRateAndStuNameLike(teacher.getClassName(),"未接收",stuname,pageable);
                jsonObject = (JSONObject) JSON.toJSON(unreceive);
                return jsonObject;
            case "received":
                if (stuname.equals("")){
                    Page<Tasks> received = taskService.findTasksByClassNameAndRate(teacher.getClassName(),"已接收",pageable);
                    jsonObject = (JSONObject) JSON.toJSON(received);
                    return jsonObject;
                }else{
                    stuname = "%" + stuname + "%";
                }
                Page<Tasks> received = taskService.findTasksByClassNameAndRateAndStuNameLike(teacher.getClassName(),"已接收",stuname,pageable);
                jsonObject = (JSONObject) JSON.toJSON(received);
                return jsonObject;
            case "finallly":
                if (stuname.equals("")){
                    Page<Tasks> finallly = taskService.findTasksByClassNameAndRate(teacher.getClassName(),"已完成",pageable);
                    jsonObject = (JSONObject) JSON.toJSON(finallly);
                    return jsonObject;
                }else{
                    stuname = "%" + stuname + "%";
                }
                Page<Tasks> finallly = taskService.findTasksByClassNameAndRateAndStuNameLike(teacher.getClassName(),"已完成",stuname,pageable);
                jsonObject = (JSONObject) JSON.toJSON(finallly);
                return jsonObject;
        }
        return (JSONObject) JSON.toJSON(new MyErrors("任务状态查询异常"));
    }
    @RequestMapping("/api/teacher/findHealthy")
    @ResponseBody
    public JSON findHealthy(@RequestParam Map<String,Object> params){
        int page = params.get("page") == null ? 0 : Integer.valueOf(params.get("page").toString());
        int size = params.get("size") == null ? 10 : Integer.valueOf(params.get("size").toString());
        String stuname = params.get("stuname") == null ? "" : params.get("stuname").toString();
        String teacheruser = params.get("teacheruser") == null ? "" : params.get("teacheruser").toString();
        Pageable pageable = PageRequest.of(page,size);
        Teacher teacher = teacherService.findByUserName(teacheruser);
        if (teacher == null||teacher.getClassName().equals("")||teacher.getClassName() == null){
            return (JSONObject) JSON.toJSON(new MyErrors("查询出现异常，请联系管理员查看绑定班级信息"));
        }
        if (stuname.equals("")){
            Page<Healthy> finallly = healthyService.findHealthiesByClassNameLike(pageable,teacher.getClassName());
            return (JSONObject) JSON.toJSON(finallly);
        }else{
            stuname = "%" + stuname + "%";
            Page<Healthy> finallly = healthyService.findHealthiesByClassNameAndStuNameLike(pageable,teacher.getClassName(),stuname);
            return (JSONObject) JSON.toJSON(finallly);
        }
    }
    @RequestMapping("/api/teacher/findByUserNamefill")
    @ResponseBody
    public JSON findByUserNamefill(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        if(username.equals("")){
            return (JSONObject) JSON.toJSON(new MyErrors("error"));
        }
        Teacher teacher = teacherService.findByUserName(username);
        if (teacher == null){
            return (JSONObject) JSON.toJSON(new MyErrors("unexist"));
        }
        return (JSON) JSON.toJSON(teacher);
    }

}
