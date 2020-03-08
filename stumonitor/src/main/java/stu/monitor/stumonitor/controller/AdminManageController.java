package stu.monitor.stumonitor.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import stu.monitor.stumonitor.pojo.MyErrors;
import stu.monitor.stumonitor.pojo.User;
import stu.monitor.stumonitor.service.ManageService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin
public class AdminManageController {
    @Resource
    ManageService manageService;
    @RequestMapping("/api/admin/findUsersByRole")
    @ResponseBody
    public JSON findUsersByRole(@RequestParam Map<String,Object> params){
        int page = params.get("page") == null ? 0 : Integer.valueOf(params.get("page").toString());
        int size = params.get("size") == null ? 10 : Integer.valueOf(params.get("size").toString());
        String role = params.get("role") == null ? "" : params.get("role").toString();
        JSONObject jsonObject;
        if (role.equals("")){
            jsonObject = (JSONObject) JSON.toJSON(new MyErrors("查询过程出现错误"));
            return jsonObject;
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<User> userPage = manageService.findUsersByRole(role,pageable);
        jsonObject = (JSONObject) JSON.toJSON(userPage);
        return jsonObject;
    }
}
