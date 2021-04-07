package com.Secondgood.secondhang.good.service;


import com.Secondgood.secondhang.good.dao.CartAlreadyBuyDao;
import com.Secondgood.secondhang.good.dao.CommentDao;
import com.Secondgood.secondhang.good.dao.GoodOfUserDao;
import com.Secondgood.secondhang.good.dao.TokenDao;
import com.Secondgood.secondhang.good.entity.*;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    @Resource
    CommentDao commentDao;

    @Resource
    GoodOfUserDao goodOfUserDao;

    @Resource
    CartAlreadyBuyDao cartAlreadyBuyDao;

    @Resource
    TokenDao tokenDao;


    /**
     * 发布评论
     * @param tokenid
     * @param goodsid
     * @param content
     * @return
     * @throws SecondRuntimeException
     */
    public String creatComment(String tokenid, String goodsid,String content) throws SecondRuntimeException {

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();


        List<GoodOfUserEntity>  check = goodOfUserDao.findByGoodsid(goodsid);
        List<GoodOfUserEntity>  check1 = goodOfUserDao.findByUserid(userid);
        List<CartAlreadyBuyEntity> check2 = cartAlreadyBuyDao.findByUseridAndGoodsid(userid,goodsid);
        if(check.size() == 0 || check1.size() ==0){
            throw new SecondRuntimeException("商品或用户不存在，无法评论");

        }
        if(check2.size() == 0 ){

            throw new SecondRuntimeException("只能评价购买过的商品");

        }
        String commentid = Util.getUniqueId();
        commentDao.save(new CommentEntity(commentid,userid,goodsid,content, Util.getNowTime()));
        return commentid;

    }

    /**
     * 评论时间排序（all）
     * @param Entity
     * @return
     */
    public List<CommentEntity> findAllByDate(List<CommentEntity> Entity) {
        List<CommentEntity> list = commentDao.findAll();

        list.sort((o1, o2) -> Util.compareDateFromString(o1.getTime(), o2.getTime()));

        return list;
    }

    /**
     * 评论时间排序（tokenid)
     * @param tokenid
     * @return
     */
    public List<CommentEntity> findByuserid(String tokenid) {

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();

        List<CommentEntity> list = commentDao.findByUserid(userid);
        list.sort((o1, o2) -> Util.compareDateFromString(o1.getTime(), o2.getTime()));
        return list;
    }


    /**
     * 评论时间排序（goodsid）
     * @param goodsid
     * @return
     */
    public List<CommentEntity> findBygoodsi(String goodsid) {
        List<CommentEntity> list = commentDao.findByGoodsid(goodsid);

        list.sort((o1, o2) -> Util.compareDateFromString(o1.getTime(), o2.getTime()));

        return list;
    }

    /**
     * 评论时间排序（tokenid, goodsid)
     * @param tokenid
     * @param goodsid
     * @return
     */
    public List<CommentEntity> findByuseridAndgoodsid(String tokenid,String goodsid) {
        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();

        List<CommentEntity> list = commentDao.findByUseridAndGoodsid(userid,goodsid);
        list.sort((o1, o2) -> Util.compareDateFromString(o1.getTime(), o2.getTime()));

        return list;
    }


    /**
     * 管理员删除评论
     * @param commentid
     * @throws SecondRuntimeException
     */

   @Transactional
    public void  removeComment(String commentid) throws SecondRuntimeException{

        List<CommentEntity> check = commentDao.findByCommentid(commentid);

        if(check.size() == 0){
            throw  new SecondRuntimeException("评论不存在");
        }
        commentDao.deleteByCommentid(commentid);

    }

    /**
     * 用户删除评论
     * @param tokenid
     * @param commentid
     * @throws SecondRuntimeException
     */
    @Transactional
    public void  removeCommentofuser(String tokenid, String commentid) throws SecondRuntimeException{

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);

        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();

        List<CommentEntity> check = commentDao.findByUseridAndCommentid(userid,commentid);
        if(check.size() == 0){
            throw  new SecondRuntimeException("用户未发表此评论，没有权限删除");
        }
        commentDao.deleteByCommentid(commentid);

    }





}
