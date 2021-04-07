package com.Secondgood.secondhang.good.controller;


import com.Secondgood.secondhang.good.dao.GoodOfUserDao;
import com.Secondgood.secondhang.good.dao.SearchDao;
import com.Secondgood.secondhang.good.service.SeegoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(description = "获取用户id/name")
@RestController
public class GoodOfuserController {
    @Resource
    GoodOfUserDao dao;


    @ApiOperation(value = "获取用户id")
    @RequestMapping(value = "/gainUserid/{goodsid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAlltype(@PathVariable(value = "goodsid") String goodsid) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", dao.findByGoodsid(goodsid));

        return map;

    }
    //@ApiOperation(value = "获取用户id")





}
