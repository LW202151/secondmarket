package com.Secondgood.secondhang.good.controller;

import com.Secondgood.secondhang.good.dao.V2UserDao;
import com.Secondgood.secondhang.good.entity.V2User;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.EmailCodeService;
import com.Secondgood.secondhang.good.service.V2UserService;
import com.Secondgood.secondhang.good.service.inner.InnerUser;
import com.sun.java.swing.plaf.windows.WindowsSpinnerUI;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(description = "用户接口")
public class V2UserController {

    @Resource
    private V2UserService userService;

    @Resource
    V2UserDao v2UserDao;

    @Resource
    EmailCodeService emailCodeService;

    /**
     * 注册
     * @param email
     * @param name
     * @param password
     * @param emailcode
     * @return
     */
    @ApiOperation(value = "注册")
    @ResponseBody
    @RequestMapping(value = "/user/register/{email}/{name}/{password}/{emailcode}", method = RequestMethod.GET)
    public Map<String, Object> register(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "name") String name,
                                        @PathVariable(value = "password") String password,
                                        @PathVariable(value = "emailcode") String emailcode) {
        Map<String, Object> map = new HashMap<>();

        try {
            String userId =  userService.register(email, name, password,emailcode);

            setMapFromUserId(map, userId);
            map.put("msg", "注册成功");

        } catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 登陆
     * @param email
     * @param password
     * @return
     */
    @ApiOperation(value = "登陆")
    @ResponseBody
    @RequestMapping(value = "/user/login/{email}/{password}", method = RequestMethod.GET)
    public Map<String, Object> login(@PathVariable(value = "email") String email, @PathVariable("password") String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("登录成功", userService.login(email, password));
        return map;
    }


    /**
     * 用户信息
     * @param tonkenid
     * @return
     */
    @ApiOperation(value = "用户信息")
    @ResponseBody
    @RequestMapping(value = "/user/info/{tonkenid}", method = RequestMethod.GET)
    public Map<String, Object> getInfo(@PathVariable(value = "tonkenid") String tonkenid) {
        Map<String, Object> map = new HashMap<>();
       map.put("code", 0);
       map.put("data", userService.getUserInfo(userService.userInfo(tonkenid)));

        return map;
    }

    /**
     * 所有用户信息
     * @return
     */
    @ApiOperation(value = "所有用户信息")
    @ResponseBody
    @RequestMapping(value = "/user/all/", method = RequestMethod.GET)
    public Map<String, Object> allUser() {
        Map<String, Object> map = new HashMap<>();
       // List<V2User> list =v2UserDao.findAll();
        map.put("code", 0);
        map.put("data", v2UserDao.findAll());

        return map;
    }

    /**
     * 验证码
     * @param email
     * @return
     * @throws MessagingException
     * @throws javax.mail.MessagingException
     */
    @ApiOperation(value = "验证码")
    @ResponseBody
    @RequestMapping(value = "/get/code/{email}", method = RequestMethod.GET)
    public Map<String, Object> getCode(@PathVariable(value = "email") String email) throws MessagingException, javax.mail.MessagingException {
        Map<String, Object> map = new HashMap<>();
        emailCodeService.sendEmailByInput(email);

        try {
            map.put("msg", "验证码已发送");

        } catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 修改密码
     * @param tokenid
     * @param email
     * @param password
     * @param code
     * @return
     */
    @ApiOperation(value = "修改密码")
    @ResponseBody
    @RequestMapping(value ="user/changePassword/{tokenid}/{email}/{password}/{code}",method = RequestMethod.GET)
    public Map<String, Object> changePassword(@PathVariable(value="tokenid") String tokenid,
                                              @PathVariable(value="email") String email,
                                              @PathVariable(value="password") String password,
                                              @PathVariable(value = "code") String code) {

        Map<String, Object> map = new HashMap<>();

        userService.changePassword(tokenid, email,password, code );
        map.put("msg", "success");
        return map;
    }

    /**
     * 忘记密码
     * @param email
     * @param password
     * @param code
     * @return
     */
    @ApiOperation(value = "忘记密码")
    @ResponseBody
    @RequestMapping(value ="user/forgetPassword/{email}/{password}/{code}",method = RequestMethod.GET)
    public Map<String, Object> changePassword(@PathVariable(value="email") String email,
                                              @PathVariable(value="password") String password,
                                              @PathVariable(value = "code") String code) {

        Map<String, Object> map = new HashMap<>();

        //userService.forgetPassword( email,password, code );
        map.put("password", userService.forgetPassword( email,password, code ));
        return map;
    }


    /**
     * 退出登录
     * @param tokenid
     * @return
     */
    @ApiOperation(value = "退出登录")
    @ResponseBody
    @RequestMapping(value ="user/exit/{tokenid}",method = RequestMethod.GET)
    public Map<String, Object> exit(@PathVariable(value="tokenid") String tokenid
                                             ) {

        Map<String, Object> map = new HashMap<>();

        userService.exit(tokenid);
        map.put("msg", "success");
        return map;
    }



    private void setMapFromUserId(Map<String, Object> map, String userId) {
        if (userId == null) {
            map.put("code", 1);
            map.put("user_id", null);
        }
        else {
            map.put("code", 0);
            map.put("user_id", userId);
        }


    }



}
