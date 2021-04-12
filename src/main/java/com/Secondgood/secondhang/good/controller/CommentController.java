package com.Secondgood.secondhang.good.controller;


import com.Secondgood.secondhang.good.dao.CommentDao;
import com.Secondgood.secondhang.good.exceptions.GoodNotFoundException;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(description = "评论接口")
@RestController
public class CommentController {

   @Resource
    CommentService commentService;
   @Resource
    CommentDao commentDao;

    /**
     * 发布评论
     * @param tokenid
     * @param goodsid
     * @param content
     * @return
     */
    @ApiOperation(value = "发表评论")
    @ResponseBody
    @RequestMapping(value = "/creatComment/{tokenid}/{goodsid}/{content}", method = RequestMethod.GET)
    public Map<String, Object> creatComment(@PathVariable(value = "tokenid") String tokenid,
                                       @PathVariable(value = "goodsid") String goodsid,
                                       @PathVariable(value = "content") String content) {

        Map<String, Object> map = new HashMap<>();


        try {
            map.put("code", 0);
            map.put("commentId", commentService.creatComment(tokenid, goodsid, content));
        } catch (SecondRuntimeException e) {
            map.put("code", 1);
            map.put("msg", e.getMessage());
        }
        return map;

    }

    /***
     * 删除评论（管理员）
     * @param commentid
     * @return
     */
    @ApiOperation(value = "删除评论（管理员）")
    @ResponseBody
    @RequestMapping(value = "/removeComment/manager/{commentid}", method = RequestMethod.GET)
    public Map<String, Object> removeComment(@PathVariable(value = "commentid") String commentid) {

        Map<String, Object> map = new HashMap<>();
        try {
            commentService.removeComment(commentid);
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
     * 删除评论（用户）
     * @param tokenid
     * @param commentid
     * @return
     */
    @ApiOperation(value = "删除评论（用户）")
    @ResponseBody
    @RequestMapping(value = "/removeComment/user/{tokenid}/{commentid}", method = RequestMethod.GET)
    public Map<String, Object> removeCommentofuser(@PathVariable(value = "tokenid") String tokenid,
                                                    @PathVariable(value = "commentid") String commentid) {

        Map<String, Object> map = new HashMap<>();

        try {
            commentService.removeCommentofuser(tokenid,commentid);
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
      * 查看用户已发表的评论
      * @param tokenid
     * @return
     */
    @ApiOperation(value = "查看用户已发表的评论")
    @ResponseBody
    @RequestMapping(value = "/userofComment/{tokenid}", method = RequestMethod.GET)
    public Map<String, Object> userofComment(@PathVariable(value = "tokenid") String tokenid){

        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", commentService.commentInfo(commentService.findByuserid(tokenid)));

        return map;

    }

    /**
     * 查看用户已发表的相关商品评论
     * @param tokenid
     * @param goodsid
     * @return
     */
    @ApiOperation(value = "查看用户已发表的相关商品评论")
    @ResponseBody
    @RequestMapping(value = "/userofComment/good/{tokenid}/{goodsid}", method = RequestMethod.GET)
    public Map<String, Object> userGoodComment(@PathVariable(value = "tokenid") String tokenid ,
                                               @PathVariable(value = "goodsid") String goodsid){

        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", commentService.findByuseridAndgoodsid(tokenid, goodsid));

        return map;

    }

    /***
     * 查看商品相关的评论
     * @param goodsid
     * @return
     */
    @ApiOperation(value = "查看商品相关的评论")
    @ResponseBody
    @RequestMapping(value = "/goodofComment/{goodsid}", method = RequestMethod.GET)
    public Map<String, Object> goodofComment(@PathVariable(value = "goodsid") String goodsid){

        Map<String, Object> map = new HashMap<>();

        map.put("code", 0);

        map.put("data", commentService.findBygoodsi(goodsid));

        return map;

    }

    /**
     * 查看所有评论
     * @return
     */
    @ApiOperation(value = "查看所有评论")
    @ResponseBody
    @RequestMapping(value = "/commit/all/", method = RequestMethod.GET)
    public Map<String, Object> allcommit() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", commentService.commentInfo(commentService.findAllByDate()));

        return map;
    }

}
