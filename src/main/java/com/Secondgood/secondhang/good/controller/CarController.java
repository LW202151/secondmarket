package com.Secondgood.secondhang.good.controller;

import com.Secondgood.secondhang.good.dao.CarDao;
import com.Secondgood.secondhang.good.exceptions.GoodNotFoundException;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.CarService;
import com.Secondgood.secondhang.good.service.SeegoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "购物车接口")
@RestController
public class CarController {


    @Resource
    CarDao dao;

    @Resource
    SeegoodsService goodService;

    @Resource
    CarService carService;

    /**
     * 加入购物车
     * @param tokenid
     * @param goodId
     * @return
     */
    @ApiOperation(value = "加入购物车")
    @RequestMapping(value = "/post/car/{tokenid}/{goodId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> create(@PathVariable(value = "tokenid") String tokenid,
                                      @PathVariable(value="goodId") String goodId) {

        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        try {
            carService.createCar(tokenid, goodId);
        }
        catch (SecondRuntimeException e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;
    }


    /**
     * 移除购物车商品
     * @param goodsid
     * @return
     */
    @ApiOperation(value = "移除购物车商品")
    @RequestMapping(value = "/remove/car/{tokenid}/{goodsid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> remove(@PathVariable(value = "tokenid") String tokenid,
                                      @PathVariable(value = "goodsid") String goodsid) {

        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        carService.remove(tokenid,goodsid);
        return map;
    }


    /**
     * 获取购物车列表
     * @param tokenid
     * @return
     */
    @ApiOperation(value = "获取用户的购物车列表")
    @RequestMapping(value = "/get/car/{tokenid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAll(@PathVariable(value = "tokenid") String tokenid) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("data", goodService.fromEntityListGetInnerGoodList(carService.getAllCar(tokenid)));

        return map;

    }

    /**
     * 获取用户卖出的所有Good
     * @param tokenid
     * @return
     */
    @ApiOperation(value = "获取用户卖出的所有Good")
    @RequestMapping(value = "/selllist/{tokenid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getWhatISell(@PathVariable(value = "tokenid") String tokenid) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("data", goodService.fromEntityListGetInnerGoodList(goodService.getAlreadySell(tokenid)));

        return map;

    }

    /**
     * 获取用户买过的所有Good
     * @param tokenid
     * @return
     */
    @ApiOperation(value = "获取用户买过的所有Good")
    @RequestMapping(value = "/buylist/{tokenid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getWhatIBuy(@PathVariable(value = "tokenid") String tokenid) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("data", goodService.fromEntityListGetInnerGoodList(goodService.getAlreadyBuy(tokenid)));

        return map;

    }

    /**
     * 获取用户未卖出的所有Good
     * @param tokenid
     * @return
     */
    @ApiOperation(value = "获取用户未卖出的所有Good")
    @RequestMapping(value = "/Noselllist/{tokenid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getWhatINoSell(@PathVariable(value = "tokenid") String tokenid) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("data", goodService.fromEntityListGetInnerGoodList(goodService.getUserNotSell(tokenid)));

        return map;

    }





}
