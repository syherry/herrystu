package stu.monitor.stumonitor.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import stu.monitor.stumonitor.pojo.ClassInfo;
import stu.monitor.stumonitor.pojo.MyErrors;
import stu.monitor.stumonitor.pojo.Teacher;
import stu.monitor.stumonitor.pojo.User;
import stu.monitor.stumonitor.service.ClassInfoService;
import stu.monitor.stumonitor.service.LoginService;
import stu.monitor.stumonitor.service.ManageService;
import stu.monitor.stumonitor.service.TeacherService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class AdminManageController {
    @Resource
    ManageService manageService;
    @Resource
    LoginService loginService;
    @Resource
    TeacherService teacherService;
    @Resource
    ClassInfoService classInfoService;
    @RequestMapping("/api/admin/findUsersByRole")
    @ResponseBody
    public JSON findUsersByRole(@RequestParam Map<String,Object> params){
        int page = params.get("page") == null ? 0 : Integer.valueOf(params.get("page").toString());
        int size = params.get("size") == null ? 10 : Integer.valueOf(params.get("size").toString());
        String role = params.get("role") == null ? "" : params.get("role").toString();
        String username = params.get("username") == null ? "" : params.get("username").toString();
        JSONObject jsonObject;
        if (role.equals("")){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("查询过程出现错误"));
            return jsonObject;
        }
        Pageable pageable = PageRequest.of(page,size);
        if (username.equals("")){
            Page<User> userPage = manageService.findUsersByRole(role,pageable);
            jsonObject = (JSONObject) JSON.toJSON(userPage);
            return jsonObject;
        }else{
            username = "%" + username + "%";
        }
        Page<User> userPage = manageService.findUserByRoleAndUsernameLike(role,username,pageable);
        jsonObject = (JSONObject) JSON.toJSON(userPage);
        return jsonObject;
    }
    @RequestMapping("/api/admin/saveinit")
    @ResponseBody
    public JSON saveinit(@RequestParam Map<String,Object> params){
        String users = params.get("users") == null ? "" : params.get("users").toString();
        JSONObject jsonObject;
        if (users.equals("")){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("查询过程出现错误"));
            return jsonObject;
        }
        List<User> list = JSONArray.parseArray(users, User.class);
        for (User user:list){
            user.setPassword(user.getUsername());
            loginService.saveUser(user);
        }
        return (JSONObject)JSON.toJSON(list.get(0));
    }
    @RequestMapping("/api/admin/findClasses")
    @ResponseBody
    public JSON findClasses(@RequestParam Map<String,Object> params){
        int page = params.get("page") == null ? 0 : Integer.valueOf(params.get("page").toString());
        int size = params.get("size") == null ? 10 : Integer.valueOf(params.get("size").toString());
        String classname = params.get("classname") == null ? "" : params.get("classname").toString();
        JSONObject jsonObject;
        Pageable pageable = PageRequest.of(page,size);
        if (classname.equals("")){
            Page<ClassInfo> classInfoPage = classInfoService.findall(pageable);
            jsonObject = (JSONObject) JSON.toJSON(classInfoPage);
            return jsonObject;
        }else{
            classname = "%" + classname + "%";
        }
        Page<ClassInfo> classInfoPage = classInfoService.findClassInfosByClassName(classname,pageable);
        jsonObject = (JSONObject)JSON.toJSON(classInfoPage);
        return jsonObject;
    }
    @RequestMapping("/api/admin/insertClasses")
    @ResponseBody
    public JSON insertClasses(@RequestParam Map<String,Object> params){
        String classname = params.get("classname") == null ? "" : params.get("classname").toString();
        String college = params.get("college") == null ? "" : params.get("college").toString();
        String department = params.get("department") == null ? "" : params.get("department").toString();
        String instructor = params.get("instructor") == null ? "" : params.get("instructor").toString();
        JSONObject jsonObject;
        if (classname.equals("")||college.equals("")||department.equals("")||instructor.equals("")){
            jsonObject =  (JSONObject) JSON.toJSON(new MyErrors("不能输入空值!"));
            return jsonObject;
        }
        ClassInfo findClass = classInfoService.findByClassNameAndCollegeAndDepartment(classname,college,department);
        if (findClass!= null ){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("当前班级已经存在！"));
            return jsonObject;
        }
        ClassInfo classInfo = new ClassInfo();
        classInfo.setClassName(classname);
        classInfo.setCollege(college);
        classInfo.setDepartment(department);
        classInfo.setInstructor(instructor);
        classInfoService.saveClassInfo(classInfo);
        jsonObject = (JSONObject)JSON.toJSON(classInfo);
        return jsonObject;
    }
    @RequestMapping("/api/admin/updateClasses")
    @ResponseBody
    public JSON updateClasses(@RequestParam Map<String,Object> params){
        String classname = params.get("classname") == null ? "" : params.get("classname").toString();
        String college = params.get("college") == null ? "" : params.get("college").toString();
        String department = params.get("department") == null ? "" : params.get("department").toString();
        String instructor = params.get("instructor") == null ? "" : params.get("instructor").toString();
        JSONObject jsonObject;
        if (classname.equals("")||college.equals("")||department.equals("")||instructor.equals("")){
            jsonObject =  (JSONObject) JSON.toJSON(new MyErrors("不能输入空值!"));
            return jsonObject;
        }
        ClassInfo findClass = classInfoService.findByClassNameAndCollegeAndDepartment(classname,college,department);
        if (findClass == null ){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("修改出现异常！"));
            return jsonObject;
        }
        Teacher teacher = teacherService.findByTeacherName(instructor);
        if ( teacher == null ){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("输入教师名称没有绑定账号，清联系教师绑定！"));
            return jsonObject;
        }
        Teacher bakTeacher = teacherService.findByTeacherName(findClass.getInstructor());
        if (bakTeacher == null ){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("修改出现异常！"));
            return jsonObject;
        }
        bakTeacher.setClassName("");
        teacher.setClassName(findClass.getClassName());
        findClass.setInstructor(instructor);
        classInfoService.saveClassInfo(findClass);
        teacherService.save(teacher);
        teacherService.save(bakTeacher);
        jsonObject = (JSONObject)JSON.toJSON(findClass);
        return jsonObject;
    }
}
