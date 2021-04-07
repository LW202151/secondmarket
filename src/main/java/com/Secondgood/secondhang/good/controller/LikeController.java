package com.Secondgood.secondhang.good.controller;


import com.Secondgood.secondhang.good.dao.LikeDao;
import com.Secondgood.secondhang.good.dao.SearchDao;
import com.Secondgood.secondhang.good.entity.LikeEntity;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.LikeService;
import com.Secondgood.secondhang.good.service.SeegoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(description = "收藏接口")
@RestController
public class LikeController {


    @Resource
    LikeDao dao;

    @Resource
    SeegoodsService goodService;

    @Resource
    LikeService likeService;


    /**
     * 添加收藏
     * @param tokenid
     * @param goodId
     * @return
     */
    @ApiOperation(value = "添加收藏")
    @RequestMapping(value = "/post/like/{tokenid}/{goodId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> create(@PathVariable(value = "tokenid") String tokenid,
                                      @PathVariable(value="goodId") String goodId) {

        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        try {
            likeService.createLike(tokenid, goodId);
        }
        catch (SecondRuntimeException e) {
            map.put("code", 0);
            map.put("msg", e.getMessage());
        }
        return map;

    }

    /**
     * 删除收藏
     * @param tokenid
     * @param goodsid
     * @return
     */
    @ApiOperation(value = "删除收藏")
    @RequestMapping(value = "/remove/like/{tokenid}/{goodsid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> remove(@PathVariable(value = "tokenid") String tokenid,
                                      @PathVariable(value = "goodsid") String goodsid) {

        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        likeService.remove(tokenid,goodsid);
        return map;
    }


    /**
     * 获取用户的收藏列表
     * @param tokenid
     * @return
     */
    @ApiOperation(value = "获取用户的收藏列表")
    @RequestMapping(value = "/get/like/{tokenid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAll(@PathVariable(value = "tokenid") String tokenid) {


        Map<String, Object> map = new HashMap<>();
        map.put("data", goodService.fromEntityListGetInnerGoodList(likeService.getAllLike(tokenid)));

        return map;

    }

}
