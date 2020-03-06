package stu.monitor.stumonitor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import stu.monitor.stumonitor.pojo.MyErrors;
import stu.monitor.stumonitor.pojo.User;
import stu.monitor.stumonitor.service.JobService;
import stu.monitor.stumonitor.service.LoginService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {
    @Resource
    private LoginService loginService;
    @Resource
    private JobService jobService;
    @RequestMapping("/api/login")
    @ResponseBody
    public  JSON login(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        String passwd = params.get("passwd") == null ? "" : params.get("passwd").toString();
        String role = params.get("role") == null ? "" : params.get("role").toString();
        JSONObject jsonObject;
        if (username.equals("")||passwd.equals("")){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("请输入用户名密码"));
            return jsonObject;
        }
        User user = loginService.findUserAndPasswdAndRole(username,passwd,role);
        if (user != null){
            jsonObject = (JSONObject) JSON.toJSON(user);
        }else {
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("用户名密码输入错误"));
            return jsonObject;
        }
        return jsonObject;
    }
    @RequestMapping("/api/updatePasswd")
    @ResponseBody
    public JSON updatePasswd(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        String passwd = params.get("passwd") == null ? "" : params.get("passwd").toString();
        String role = params.get("role") == null ? "" : params.get("role").toString();
        String inputPassword = params.get("inputPassword") == null ? "" : params.get("inputPassword").toString();
        JSONObject jsonObject;
        User user = loginService.findUserAndPasswdAndRole(username,passwd,role);
        user.setPassword(inputPassword);
        loginService.saveUser(user);
        jsonObject = (JSONObject) JSON.toJSON(new MyErrors("修改密码成功"));
        return jsonObject;
    }
    @RequestMapping("/api/register")
    @ResponseBody
    public JSON register(@RequestParam Map<String,Object> params){
        String username = params.get("username") == null ? "" : params.get("username").toString();
        String passwd = params.get("passwd") == null ? "" : params.get("passwd").toString();
        String role = params.get("role") == null ? "" : params.get("role").toString();
        JSONObject jsonObject;
        if (username.equals("")||passwd.equals("")){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("请输入用户名密码"));
            return jsonObject;
        }
        User user = loginService.findUserAndRole(username,role);
        if (user != null){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("当前账号已经被注册"));
            return jsonObject;
        }
        User saveUser = new User();
        saveUser.setUsername(username);
        saveUser.setPassword(passwd);
        saveUser.setRole(role);
        loginService.saveUser(saveUser);
        jsonObject = (JSONObject) JSON.toJSON(saveUser);
        return jsonObject;
    }
}
