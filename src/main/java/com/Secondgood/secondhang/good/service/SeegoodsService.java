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

    @Resource
    ScoreDao scoreDao;

    @Resource
    OrderofgoodDao orderofgoodDao;

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
    public String postGood(String name, String tag, String desc, float price, String type, String tokenid) throws SecondRuntimeException {

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);

        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();
        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户id不存在，无法发布商品，请注册");
        }

        String goodsid = Util.getUniqueId();

        seegoodsDao.save(new GoodsEntity(goodsid, name, tag, desc, price,type,Util.getNowTime()));
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

    /**
     * 商品详情（用户）
     * @param tokenid
     * @param goodsid
     * @return
     */
    public  List<GoodsEntity> scanGood(String tokenid ,String goodsid){

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();
        List<GoodsEntity> entity = seegoodsDao.findByGoodsid(goodsid);
        String tag = entity.get(0).getTag();
        List<ScoreEntity> check = scoreDao.findByUseridAndTag(userid,tag);
        if(check.size() != 0){

           int score = check.get(0).getScore();
           ScoreEntity scoreEntity = check.get(0);

           scoreEntity.setScore(score+1);
           scoreEntity.setTime(Util.getNowTime());
           scoreDao.save(scoreEntity);

        }else {
             int sum = 1;
            scoreDao.save(new ScoreEntity(Util.getUniqueId() , userid , tag ,sum , Util.getNowTime()));

        }
        return entity;

    }

    /***
     * 推荐商品
     * @param tokenid
     * @return
     */

    public List<GoodsEntity> recommend(String tokenid){

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        List<ScoreEntity> scoreEntities = scoreDao.findByUseridOrderByScoreDesc(userid);

        //分数前两个标签
        int max = scoreEntities.get(0).getScore() ;
        String tag1 = scoreEntities.get(0).getTag();

        int max2 = scoreEntities.get(1).getScore();
        String tag2 = scoreEntities.get(1).getTag();

        //总的推荐数量30
        int sum = 30 ;

        //max推荐商品数目
        int count1 = sum*max/(max+max2);

        //count用来计算当前已推荐的商品数量
        int count = 0;


        List<GoodsEntity> res = new ArrayList<>();

        //tag2包含tag1
        if(tagJudge(tag2 , tag1)) {
            //获取tag1推荐的商品
            List<GoodsEntity> maxGoodsEntity = seegoodsDao.findByTag(tag1);
            for (GoodsEntity entity : maxGoodsEntity) {
                if (count == sum) {
                    break;
                } else {
                    String goodsid = entity.getGoodsid();
                    if(goodOfUserDao.findByGoodsidAndUserid(goodsid,userid).size() == 0) {
                        res.add(entity);
                        count++;
                    }

                }
            }
        } else if(tagJudge(tag1,tag2)) {
           //tag1包含tag2
            //获取tag2推荐的商品
            List<GoodsEntity> max2GoodsEntity = seegoodsDao.findByTag(tag2);
            for (GoodsEntity entity : max2GoodsEntity) {

                if (count == sum) {
                    break;
                } else {
                    String goodsid = entity.getGoodsid();
                    if(goodOfUserDao.findByGoodsidAndUserid(goodsid,userid).size() == 0) {
                        res.add(entity);
                        count++;
                    }

                }
            }
        }else {
            //无包含关系
            //获取tag1推荐的商品
            List<GoodsEntity> maxGoodsEntity = seegoodsDao.findByTag(tag1);
            for (GoodsEntity entity : maxGoodsEntity) {

                if (count == count1) {
                    break;
                } else {
                    String goodsid = entity.getGoodsid();
                    if(goodOfUserDao.findByGoodsidAndUserid(goodsid,userid).size() == 0) {
                        res.add(entity);
                        count++;
                    }
                }
            }
            //获取tag2推荐的商品
            List<GoodsEntity> max2GoodsEntity = seegoodsDao.findByTag(tag2);
            for (GoodsEntity entity : max2GoodsEntity) {

                if (count == sum) {
                    break;
                } else {
                    String goodsid = entity.getGoodsid();
                    if(goodOfUserDao.findByGoodsidAndUserid(goodsid,userid).size() == 0) {
                        res.add(entity);
                        count++;
                    }

                }

            }
        }

        //补充商品数量（推荐后）
        if(count != sum){

            int extra = sum - count ;
            List<GoodsEntity> extraEntity = seegoodsDao.findAll();

            for (GoodsEntity entity : extraEntity){

                if(extra == 0){
                    break;
                }else {
                    if(!res.contains(entity)){
                        String goodsid = entity.getGoodsid();
                        if(goodOfUserDao.findByGoodsidAndUserid(goodsid,userid).size() == 0) {
                            res.add(entity);
                            extra--;
                        }
                    }

                }
            }

        }

        return res;

    }

    /**
     * 判断tag是否有包含关系
     * @param tag1
     * @param tag2
     * @return
     */

    public boolean tagJudge(String tag1 , String tag2){

        int i = 0 ;

        int j =0 ;
        while(i<tag1.length() )
        {
            if(tag1.charAt(i)==tag2.charAt(j))
            {
                i++;j++;
            }
            else
            {
                i=i-j+1;
                j=0;
            }
            if(j==tag2.length())
            {
                return true;
                // break ;
            }
        }
        return false;
    }


    




}