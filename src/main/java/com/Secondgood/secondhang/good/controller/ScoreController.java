package com.Secondgood.secondhang.good.controller;


import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.SeegoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "推荐接口")
public class ScoreController {


    @Resource
    SeegoodsService seegoodsService;

    @ApiOperation(value = "推荐商品")
    @RequestMapping(value = "/recommend/good/{tokenid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> recommend(@PathVariable(value = "tokenid") String tokenid){

        Map<String, Object> map = new HashMap<>();


        map.put("code", 0);
        try {
            map.put("recommend", seegoodsService.fromEntityListGetInnerGoodList(seegoodsService.recommend(tokenid)));
        }catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }
        return map;

    }

}
