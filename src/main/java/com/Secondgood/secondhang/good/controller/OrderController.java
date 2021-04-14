package com.Secondgood.secondhang.good.controller;


import com.Secondgood.secondhang.good.dao.OrderDao;
import com.Secondgood.secondhang.good.exceptions.GoodNotFoundException;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "订单接口")
public class OrderController {

    @Resource
    CarService carService;

    @Resource
    OrderDao orderDao;


    /**
     * 创建订单
     * @param tokenid
     * @param addressid
     * @param body
     * @return
     */
    @ApiOperation(value = "创建订单")
    @RequestMapping(value = "/createOrder/{tokenid}/{addressid}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createOrder(@PathVariable(value = "tokenid") String tokenid,
                                           @PathVariable(value="addressid" )  String addressid,
                                           @RequestBody JSONObject body) {


        Map<String, Object> map = new HashMap<>();

        // deal
        ArrayList<String> res = (ArrayList<String>) body.get("data");
        try {
            map.put("orderid", carService.createOrder(res, tokenid,addressid));
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;

    }

    /**
     * 查看所有订单
     * @return
     */

    @ApiOperation(value = "查看所有订单")
    @ResponseBody
    @RequestMapping(value = "/order/all/", method = RequestMethod.GET)
    public Map<String, Object> allorder() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", carService.findAllByDate());

        return map;
    }

    /**
     * 查看订单(用户）
     * @param tokenid
     * @return
     */
    @ApiOperation(value = "查看订单(用户）")
    @ResponseBody
    @RequestMapping(value = "/order/user/{tokenid}", method = RequestMethod.GET)
    public Map<String, Object> userorder(@PathVariable(value = "tokenid") String tokenid) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", carService.findAllByusreid(tokenid));

        return map;
    }

    /**
     * 删除订单(管理员）
     * @param orderid
     * @return
     */
    @ApiOperation(value = "删除订单(管理员）")
    @ResponseBody
    @RequestMapping(value = "/deleteorder/manager/{token}/{orderid}", method = RequestMethod.GET)
    public Map<String, Object> deletorder(
            @PathVariable(value = "token") String token,@PathVariable(value = "orderid") String orderid) {
        Map<String, Object> map = new HashMap<>();

        try {
            carService.deleteorder(token ,orderid);
            map.put("code", "删除成功");
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;
    }


    /**
     * 删除订单(用户）
     * @param tokenid
     * @param orderid
     * @return
     */
    @ApiOperation(value = "删除订单(用户）")
    @ResponseBody
    @RequestMapping(value = "/deleteorder/user/{tokenid}/{orderid}", method = RequestMethod.GET)
    public Map<String, Object> userdeletorder(@PathVariable(value = "tokenid") String tokenid,
            @PathVariable(value = "orderid") String orderid) {
        Map<String, Object> map = new HashMap<>();

        try {
            carService.userdeleteorder(tokenid, orderid);
            map.put("code", "删除成功");
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;
    }

}
