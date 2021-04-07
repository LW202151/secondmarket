package com.Secondgood.secondhang.good.controller;

import com.Secondgood.secondhang.good.dao.SearchDao;
import com.Secondgood.secondhang.good.exceptions.GoodNotFoundException;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.SeegoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Api(description = "商品接口")
@Controller
public class Seegoods {


    @Resource
    SearchDao dao;

    @Resource
    SeegoodsService seegoodsService;

    /***
     * 发布商品
     * @param name
     * @param tag
     * @param type
     * @param price
     * @param desc
     * @return
     */
    @ApiOperation(value = "发布商品")
    @RequestMapping(value = "/post/{name}/{tag}/{type}/{price}/{desc}/{tokenid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> post(@PathVariable(value = "name") String name,
                                    @PathVariable(value = "tag") String tag,
                                    @PathVariable(value = "type") String type,
                                    @PathVariable(value = "price") Float price,
                                    @PathVariable(value = "desc") String desc,
                                    @PathVariable(value = "tokenid") String tokenid) {


        Map<String, Object> map = new HashMap<>();



        map.put("code", 0);
        try {
            map.put("goodId", seegoodsService.postGood(name, tag,type,price, desc, tokenid));
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }
        return map;

    }

    /***
     * 所有商品信息
     * @return
     */
    @ApiOperation(value = "所有商品信息")
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAll() {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", seegoodsService.fromEntityListGetInnerGoodList(dao.findAll()));

        return map;

    }

    /***
     * 所有商品信息（时间顺序）
     * @return
     */
    @ApiOperation(value = "所有商品信息（时间顺序））")
    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAllsort() {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", seegoodsService.fromEntityListGetInnerGoodList(seegoodsService.findAllByDate()));

        return map;

    }


    /***
     * 查找商品(name)
     * @param name
     * @return
     */
    @ApiOperation(value = "查找商品(name)")
    @RequestMapping(value = "/search/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAll(@PathVariable(value = "name") String name) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", seegoodsService.fromEntityListGetInnerGoodList(dao.findByName(name)));

        return map;

    }


    /****
     * 查找商品（tag）
     * @param tag
     * @return
     */
    @ApiOperation(value = "查找商品（tag）")
    @RequestMapping(value = "/search/tag/{tag}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAlltag(@PathVariable(value = "tag") String tag) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", seegoodsService.fromEntityListGetInnerGoodList(dao.findByTag(tag)));

        return map;

    }


    /***
     * 查找商品（type）
     * @param type
     * @return
     */
    @ApiOperation(value = "查找商品（type）")
    @RequestMapping(value = "/search/type/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAlltype(@PathVariable(value = "type") String type) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", seegoodsService.fromEntityListGetInnerGoodList(dao.findByType(type)));

        return map;

    }

    /**
     *
     *      下架
     *
     *      body体的json格式
     *      {
     *         "data": [
     *              "goodId_1",
     *              "goodId_2",
     *              "goodId_3"
     *         ]
     *       }

     * @param body
     * @return
     */
    @ApiOperation(value = "下架商品（管理员）")
    @RequestMapping(value = "/delete/manager", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> under( @RequestBody JSONObject body) {


        Map<String, Object> map = new HashMap<>();
        ArrayList<String> res = (ArrayList<String>) body.get("data");

       try {
           seegoodsService.under(res);
           map.put("code", 0);
        }
        catch (GoodNotFoundException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;

    }

    /**
     * 下架商品（用户）
     * @param tokenid
     * @param body
     * @return
     */
    @ApiOperation(value = "下架商品（用户）")
    @RequestMapping(value = "/delete/user/{tokenid}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> underofuser(@PathVariable(value = "tokenid") String tokenid,
            @RequestBody JSONObject body) {


        Map<String, Object> map = new HashMap<>();
        ArrayList<String> res = (ArrayList<String>) body.get("data");

        try {
            seegoodsService.underofuser(res,tokenid);
            map.put("code", 0);
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }

        return map;

    }


    /**
     * 商品详情
     * @param goodsid
     * @return
     */
    @ApiOperation(value = "商品详情")
    @RequestMapping(value = "/information/{goodsid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> infomationGood(@PathVariable(value = "goodsid") String goodsid) {


        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);
        map.put("data", seegoodsService.fromEntityListGetInnerGoodList(dao.findByGoodsid(goodsid)));
        return map;

    }

    /**
     * 修改商品信息（管理员）
     * @param goodsid
     * @param name
     * @param tag
     * @param desc
     * @param price
     * @param type
     * @return
     */
    @ApiOperation(value = "修改商品信息（管理员）")
    @RequestMapping(value = "/changeGoods/manager/{goodsid}/{name}/{tag}/{desc}/{price}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> changeGoods(@PathVariable(value = "goodsid") String goodsid,
                                     @PathVariable(value = "name") String name,
                                    @PathVariable(value = "tag") String tag,
                                    @PathVariable(value = "desc") String desc,
                                    @PathVariable(value = "price") Float price,
                                    @PathVariable(value = "type") String type
                                    ) {


        Map<String, Object> map = new HashMap<>();



        map.put("code", 0);
        try {
            map.put("goodId", seegoodsService.changeGood(goodsid,name, tag, desc, price, type));
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }
        return map;

    }

    /**
     * 修改商品信息（用户）
     * @param tokenid
     * @param goodsid
     * @param name
     * @param tag
     * @param desc
     * @param price
     * @param type
     * @return
     */
    @ApiOperation(value = "修改商品信息（用户）")
    @RequestMapping(value = "/changeGoods/user/{tokenid}/{goodsid}/{name}/{tag}/{desc}/{price}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> changeGoodsofuser(@PathVariable(value = "tokenid") String tokenid,
                                                 @PathVariable(value = "goodsid") String goodsid,
                                                 @PathVariable(value = "name") String name,
                                                 @PathVariable(value = "tag") String tag,
                                                 @PathVariable(value = "desc") String desc,
                                                 @PathVariable(value = "price") Float price,
                                                 @PathVariable(value = "type") String type
    ) {


        Map<String, Object> map = new HashMap<>();



        map.put("code", 0);
        try {
            map.put("goodId", seegoodsService.changeGoodofuser(tokenid,goodsid,name, tag, desc, price, type));
        }
        catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }
        return map;

    }

}
