package com.Secondgood.secondhang.good.service;


import com.Secondgood.secondhang.good.dao.*;
import com.Secondgood.secondhang.good.entity.*;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService {

    @Resource
    private LikeDao likegoodsDao;
    @Resource
    private PicGoodDao picGoodDao;

    @Resource
    private SearchDao searchDao;

    @Resource
    private V2UserService v2UserService;

    @Resource
    private SeegoodsService seegoodsService;

    @Resource
    TokenDao tokenDao;

    @Resource
    ScoreDao scoreDao;

    @Resource
    GoodOfUserDao goodOfUserDao;

    /**
     * 添加收藏
     * @param tokenid
     * @param goodId
     * @throws SecondRuntimeException
     */
    public void createLike(String tokenid, String goodId) throws SecondRuntimeException {

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();



        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户id不存在，无法加入收藏列表，请注册");
        }

        if (!seegoodsService.checkGoodExist(goodId)) {
            throw new SecondRuntimeException("物品id不存在，无法加入收藏列表，请发布此物品");
        }

        List<GoodOfUserEntity> temp = goodOfUserDao.findByGoodsidAndUserid(goodId,userid);
        if(temp.size() != 0){

            throw new SecondRuntimeException("自身发布的商品不能收藏");
        }

        List<LikeEntity> check = likegoodsDao.findByUseridAndGoodsid(userid,goodId);
        if(check.size() == 1){
            throw new SecondRuntimeException("商品已被收藏");
        }

        likegoodsDao.save(new LikeEntity(userid, goodId, Util.getNowTime()));

        List<GoodsEntity> goodsEntities = searchDao.findByGoodsid(goodId);
        String tag = goodsEntities.get(0).getTag();

        List<ScoreEntity> checkentity = scoreDao.findByUseridAndTag(userid,tag);
        if(checkentity.size() != 0){

            int score = checkentity.get(0).getScore();
            ScoreEntity scoreEntity = checkentity.get(0);

            scoreEntity.setScore(score+3);
            scoreEntity.setTime(Util.getNowTime());
            scoreDao.save(scoreEntity);

        }else {
            int sum = 3;
            scoreDao.save(new ScoreEntity(Util.getUniqueId() , userid , tag ,sum , Util.getNowTime()));

        }
    }

    /**
     * 删除收藏
     * @param tokenid
     * @param goodsid
     */
    @Transactional
    public void remove(String tokenid, String goodsid) {
        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = entity.get(0).getUserid();


        List<LikeEntity> check  = likegoodsDao.findByUseridAndGoodsid(userid, goodsid);
        if(check.size() == 0){
            throw  new SecondRuntimeException("用户和商品无法对应，没有权限进行操作");
        }
        likegoodsDao.deleteBygoodsid(goodsid);
    }


    /**
     * 获取用户的收藏列表
     * @param tokenid
     * @return
     */
    public List<GoodsEntity> getAllLike(String tokenid) {

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        List<LikeEntity> temp = likegoodsDao.findByUserid(userid);
        List<GoodsEntity> result = new ArrayList<>();

        for (LikeEntity entity : temp) {
            List<GoodsEntity> op = searchDao.findByGoodsid(entity.getGoodsid());

            if (op.size() == 0) {
                continue;
            }

            result.add(op.get(0));

        }
        result.sort((o1, o2) -> Util.compareDateFromString(o1.getUpdatedate(), o2.getUpdatedate()));


        return result;
    }

}
