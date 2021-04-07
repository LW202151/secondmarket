package com.Secondgood.secondhang.good.service;

import com.Secondgood.secondhang.good.dao.*;
import com.Secondgood.secondhang.good.entity.*;
import com.Secondgood.secondhang.good.exceptions.GoodNotFoundException;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.service.inner.InnerGood;
import com.Secondgood.secondhang.good.util.COSUtil;
import com.Secondgood.secondhang.good.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SeegoodsService {

    @Resource
    private SearchDao seegoodsDao;

    @Resource
    private PicGoodDao picGoodDao;

    @Resource
    private CartAlreadyBuyDao cartAlreadyBuyDao;

    @Resource
    private GoodOfUserDao goodOfUserDao;

    @Resource
    private AlreadySellDao alreadySellDao;

    @Resource
    private V2UserService v2UserService;

    @Resource
    private CarDao carDao;

    @Resource
    private LikeDao likeDao;

    @Resource
    CommentDao commentDao;

    @Resource
    TokenDao tokenDao;

    /**
     * 判断物品是否存在
     * @param goodsid
     * @return
     */
    public boolean checkGoodExist(String goodsid) {

        return seegoodsDao.findByGoodsid(goodsid).size() != 0;
    }

    /**
     * 商品时间排序
     * @return
     */
    public List<GoodsEntity> findAllByDate() {
        List<GoodsEntity> list = seegoodsDao.findAll();

        list.sort((o1, o2) -> Util.compareDateFromString(o1.getUpdatedate(), o2.getUpdatedate()));

        return list;
    }

    /**
     * 发布商品
     * @param name
     * @param tag
     * @param type
     * @param price
     * @param desc
     * @param tokenid
     * @return
     * @throws SecondRuntimeException
     */
    public String postGood(String name, String tag, String type, float price, String desc, String tokenid) throws SecondRuntimeException {

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);

        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();
        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户id不存在，无法发布商品，请注册");
        }

        String goodsid = Util.getUniqueId();

        seegoodsDao.save(new GoodsEntity(goodsid, name, tag, type, price,desc,Util.getNowTime()));
        goodOfUserDao.save(new GoodOfUserEntity(goodsid, userid));

        return goodsid;
    }

    /**
     * 实体list换真实地址实体
     * @param list list
     * @return List&ltI;nnerGood&gt;
     */
    public List<InnerGood> fromEntityListGetInnerGoodList(List<GoodsEntity> list) {
        List<InnerGood> res = new ArrayList<>();
        for (GoodsEntity entity : list) {

            List<PicGoodEntity> temp = picGoodDao.findByGoodsid(entity.getGoodsid());
            String url = null;

            if (temp.size() == 0) {
                url = null;
            }
            else {
                url = COSUtil.getUrlFromObjectKey(temp.get(0).getPickey());
            }

            res.add(new InnerGood(entity,url));
        }

        return res;
    }


    /**
     * 用户获取所有已经买过的Good
     * @param tokenid
     * @return List
     */
    public List<GoodsEntity> getAlreadyBuy(String tokenid) {

           List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
           if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
            }
            String userid = Entity.get(0).getUserid();


            List<CartAlreadyBuyEntity> temp = cartAlreadyBuyDao.findByUserid(userid);

            List<GoodsEntity> result = new ArrayList<>();

            for (CartAlreadyBuyEntity entity : temp) {
                String goodsid = entity.getGoodsid();

                GoodsEntity real = seegoodsDao.findByGoodsid(goodsid).get(0);

                result.add(real);
            }

            return result;
        }

    /**
     * 用户获取所有已经卖出的Good
     * @param tokenid
     * @return List
     */
    public List<GoodsEntity> getAlreadySell(String tokenid) {

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();


        List<AlreadySellEntity> temp = alreadySellDao.findByUserid(userid);

        List<GoodsEntity> result = new ArrayList<>();

        for (AlreadySellEntity entity : temp) {
            String goodsid = entity.getGoodsid();

            GoodsEntity real = seegoodsDao.findByGoodsid(goodsid).get(0);

            result.add(real);
        }

        return result;
    }

    /**
     * 用户获取所有未卖出的Good
     * @param tokenid
     * @return
     */

    public List<GoodsEntity> getUserNotSell(String tokenid) {

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userId = Entity.get(0).getUserid();

        List<GoodOfUserEntity> temp = goodOfUserDao.findByUserid(userId);
        List<GoodsEntity> already = this.getAlreadySell(userId);

        List<GoodsEntity> result = new ArrayList<>();

        for (GoodOfUserEntity goodOfUserEntity : temp) {
            if (search(goodOfUserEntity.getGoodsid(), already)) {
                continue;
            }

            GoodsEntity real = seegoodsDao.findByGoodsid(goodOfUserEntity.getGoodsid()).get(0);

            result.add(real);
        }

        return result;
    }

    private boolean search(String goodid, List<GoodsEntity> already) {
        for (GoodsEntity entity : already) {
            if (entity.getGoodsid().equals(goodid)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 管理员下架商品
     * @param goodIdList
     * @throws GoodNotFoundException
     */
    @Transactional
    public void under(List<String> goodIdList) throws GoodNotFoundException {
        for (String goodid : goodIdList) {

            List<GoodOfUserEntity> temp = goodOfUserDao.findByGoodsid(goodid);

            if (temp.size() == 0) {
                throw new GoodNotFoundException("物品id不存在,无法进行下架", goodid);
            }

            // 下架商品
            seegoodsDao.deleteByGoodsid(goodid);
            carDao.deleteByGoodsid(goodid);
            cartAlreadyBuyDao.deleteByGoodsid(goodid);
            alreadySellDao.deleteByGoodsid(goodid);
            goodOfUserDao.deleteByGoodsid(goodid);
            likeDao.deleteBygoodsid(goodid);
            commentDao.deleteByGoodsid(goodid);

        }

    }

    /**
     * 用户下架商品
     * @param goodIdList
     * @param tokenid
     * @throws GoodNotFoundException
     */
    @Transactional
    public void underofuser(List<String> goodIdList, String tokenid) throws SecondRuntimeException {
        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        for (String goodid : goodIdList) {

            List<GoodOfUserEntity> temp = goodOfUserDao.findByGoodsidAndUserid(goodid,userid);

            if (temp.size() == 0) {
                throw new SecondRuntimeException("该用户未发布此商品，没有权限下架此商品");
            }

            // 下架商品
            seegoodsDao.deleteByGoodsid(goodid);
            carDao.deleteByGoodsid(goodid);
            cartAlreadyBuyDao.deleteByGoodsid(goodid);
            alreadySellDao.deleteByGoodsid(goodid);
            goodOfUserDao.deleteByGoodsid(goodid);
            likeDao.deleteBygoodsid(goodid);
            commentDao.deleteByGoodsid(goodid);

        }

    }


    /**
     * 修改商品信息（管理员）
     * @param goodsid
     * @param name
     * @param tag
     * @param desciption
     * @param price
     * @param type
     * @return
     */

    public String changeGood(String goodsid, String name,String tag,String desciption,float price , String type){
/*
      覆盖商品信息
       */
       List<GoodsEntity> check = seegoodsDao.findByGoodsid(goodsid);

        if (check.size()==0) {
            throw  new SecondRuntimeException("该商品不存在，无法修改信息");
        }

        GoodsEntity goodop = seegoodsDao.findByGoodsid(goodsid).get(0);
        goodop.setName(name);
        goodop.setTag(tag);
        goodop.setDesciption(desciption);
        goodop.setPrice(price);
        goodop.setType(type);
        goodop.setUpdatedate(Util.getNowTime());
        seegoodsDao.save(goodop);
        return goodsid;


    }

    /**
     * 修改商品信息（用户）
     * @param tokenid
     * @param goodsid
     * @param name
     * @param tag
     * @param desciption
     * @param price
     * @param type
     * @return
     */

    public String changeGoodofuser(String tokenid ,String goodsid, String name,String tag,String desciption,float price , String type){

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        /*
      覆盖商品信息
       */
        List<GoodOfUserEntity> check = goodOfUserDao.findByGoodsidAndUserid(goodsid,userid);

        if (check.size()==0) {
            throw  new SecondRuntimeException("用户未发布此商品，没有权限修改");
        }
        GoodsEntity goodop = seegoodsDao.findByGoodsid(goodsid).get(0);
        goodop.setName(name);
        goodop.setTag(tag);
        goodop.setDesciption(desciption);
        goodop.setPrice(price);
        goodop.setType(type);
        goodop.setUpdatedate(Util.getNowTime());
        seegoodsDao.save(goodop);
        return goodsid;

    }

}