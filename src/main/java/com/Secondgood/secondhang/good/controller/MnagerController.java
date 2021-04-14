package com.Secondgood.secondhang.good.controller;

import com.Secondgood.secondhang.good.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "管理员接口")
public class MnagerController {
    @Resource
    ManagerService managerService;

    @ApiOperation(value = "登陆")
    @ResponseBody
    @RequestMapping(value = "/admin/login/{name}/{password}", method = RequestMethod.GET)
    public Map<String, Object> login(@PathVariable(value = "name") String name, @PathVariable("password") String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("登录成功", managerService.login(name, password));
        return map;
    }

    @ApiOperation(value = "退出登录")
    @ResponseBody
    @RequestMapping(value ="admin/exit/{token}",method = RequestMethod.GET)
    public Map<String, Object> exit(@PathVariable(value="token") String token
    ) {

        Map<String, Object> map = new HashMap<>();
        managerService.exit(token);
        map.put("msg", "success");
        return map;
    }




}
