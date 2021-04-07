package com.Secondgood.secondhang.good.controller;


import com.Secondgood.secondhang.good.dao.AddressDao;
import com.Secondgood.secondhang.good.dao.SearchDao;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.AddressService;
import com.Secondgood.secondhang.good.service.SeegoodsService;
import com.Secondgood.secondhang.good.service.V2UserService;
import com.Secondgood.secondhang.good.service.inner.AddressBodyContent;
import com.Secondgood.secondhang.good.service.inner.AddressContent;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Api(description = "地址信息接口")
@RestController
public class AddressController {


    @Resource
    AddressDao addressdao;

    @Resource
    AddressService addressService;

    @Resource
    V2UserService v2UserService;

    /**
     * 添加地址
     * @param tokenid
     * @param name
     * @param phone
     * @param pronvice
     * @param city
     * @param detail
     * @return
     */
    @ApiOperation(value = "添加地址")
    @RequestMapping(value = "/address/create/{tokenid}/{name}/{phone}/{province}/{city}/{detail}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> createAddress(@PathVariable(value = "tokenid") String tokenid,
                                    @PathVariable(value = "name") String name,
                                    @PathVariable(value = "phone") String phone,
                                    @PathVariable(value = "province") String pronvice,
                                    @PathVariable(value = "city") String city,
                                    @PathVariable(value = "detail") String detail) {


        Map<String, Object> map = new HashMap<>();



        map.put("code", 0);
        try {
            map.put("addressid", addressService.creatAddress(tokenid,name, phone,pronvice,city,detail));
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }
        return map;

    }

    /***
     * 删除地址
     * @param tokenid
     * @param addressid
     * @return
     */
    @ApiOperation(value = "删除地址")
    @ResponseBody
    @RequestMapping(value = "/address/remove/{tokenid}/{addressid}", method = RequestMethod.GET)
    public Map<String, Object> deleteAddress(@PathVariable(value = "tokenid") String tokenid,
                                                   @PathVariable(value = "addressid") String addressid) {

        Map<String, Object> map = new HashMap<>();

        // deal
        //ArrayList<String> res = (ArrayList<String>) body.get("data");


        try {
            addressService.deleteAddress(tokenid,addressid);
            map.put("code", 0);
            map.put("msg", "删除成功");
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;
    }


    /**
     * 查看用户相关地址
     * @param tokenid
     * @return
     */
    @ApiOperation(value = "查看用户相关地址")
    @ResponseBody
    @RequestMapping(value = "/address/look/{tokenid}", method = RequestMethod.GET)
    public Map<String, Object> lookAddress(@PathVariable(value = "tokenid") String tokenid) {

        Map<String, Object> map = new HashMap<>();

        try {
            map.put("code",0);
            map.put("data", addressService.lookAddress(tokenid));

        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;
    }

    /**
     * 查看具体地址
     * @param tokenid
     * @param addressid
     * @return
     */
    @ApiOperation(value = "查看具体地址")
    @ResponseBody
    @RequestMapping(value = "/address/look/detail/{tokenid}/{addressid}", method = RequestMethod.GET)
    public Map<String, Object> look(@PathVariable(value = "tokenid") String tokenid,
                                    @PathVariable(value = "addressid") String addressid) {

        Map<String, Object> map = new HashMap<>();

        // deal
        //ArrayList<String> res = (ArrayList<String>) body.get("data");


        try {
            map.put("code",0);
            map.put("data", addressService.look(tokenid,addressid));

        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;
    }





}
