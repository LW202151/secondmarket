package com.Secondgood.secondhang.good.service;

import com.Secondgood.secondhang.good.dao.*;
import com.Secondgood.secondhang.good.entity.*;
import com.Secondgood.secondhang.good.exceptions.GoodNotFoundException;
import com.Secondgood.secondhang.good.exceptions.SecondRuntimeException;
import com.Secondgood.secondhang.good.util.Util;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Resource
    private CarDao cargoodsDao;
    @Resource
    private PicGoodDao picGoodDao;

    @Resource
    private SearchDao searchDao;

    @Resource
    private GoodOfUserDao goodOfUserDao;

    @Resource
    private CartAlreadyBuyDao cartAlreadyBuyDao;

    @Resource
    private AlreadySellDao alreadySellDao;

    @Resource
    private V2UserService v2UserService;

    @Resource
    private SeegoodsService seegoodsService;

    @Resource
    AdOfUserDao adOfUserDao;

    @Resource
    OrderDao   orderDao;

    @Resource
    OrderofuserDao orderofuserDao;

    @Resource
    AddressDao addressDao;

    @Resource
    TokenofManagerDao tokenofManagerDao;

    @Resource
    OrderofgoodDao orderofgoodDao;

    @Resource
    TokenDao tokenDao;

    @Resource
    ScoreDao scoreDao;

    @Resource
    LikeDao likeDao;

    @Resource
    CommentDao commentDao;

    /**
     * 加入购物车
     * @param tokenid
     * @param goodId
     * @throws SecondRuntimeException
     */
    public void createCar(String tokenid, String goodId) throws SecondRuntimeException {

        List<TokenEntity> entity = tokenDao.findByTokenid(tokenid);
        if(entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userId = entity.get(0).getUserid();


        List<CarEntity> check = cargoodsDao.findByUseridAndGoodsid(userId, goodId);
        if (!v2UserService.checkUserIdExist(userId)) {
            throw new SecondRuntimeException("用户id不存在，无法加入购物车，请注册");
        }

        if (!seegoodsService.checkGoodExist(goodId)) {
            throw new SecondRuntimeException("物品id不存在，无法加入购物车");
        }
        List<GoodOfUserEntity> temp = goodOfUserDao.findByGoodsidAndUserid(goodId,userId);
        if(temp.size() != 0){

            throw new SecondRuntimeException("自身发布的商品不能加购");
        }

        if(check.size() == 1){
            throw new SecondRuntimeException("商品已加入购物车");
        }

        cargoodsDao.save(new CarEntity(userId, goodId));

        List<GoodsEntity> goodsEntities = searchDao.findByGoodsid(goodId);
        String tag = goodsEntities.get(0).getTag();
        List<ScoreEntity> checkentity = scoreDao.findByUseridAndTag(userId,tag);
        if(checkentity.size() != 0){

            int score = checkentity.get(0).getScore();
            ScoreEntity scoreEntity = checkentity.get(0);

            scoreEntity.setScore(score+5);
            scoreEntity.setTime(Util.getNowTime());
            scoreDao.save(scoreEntity);

        }else {
            int sum = 5;
            scoreDao.save(new ScoreEntity(Util.getUniqueId() , userId , tag ,sum , Util.getNowTime()));

        }
    }

    /***
     * 移除购物车商品
     * @param tokenid
     * @param goodsid
     */
    @Transactional
    public void remove(String tokenid ,String goodsid) {
        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
       // String userid = Entity.get(0).getUserid();

        cargoodsDao.deleteBygoodsid(goodsid);
    }

    /**
     * 获取用户的购物车列表
     * @param tokenid
     * @return
     */

    public List<GoodsEntity> getAllCar(String tokenid) {
        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        List<CarEntity> temp = cargoodsDao.findByUserid(userid);
        List<GoodsEntity> result = new ArrayList<>();
        for (CarEntity entity : temp) {

             List < GoodsEntity > op = searchDao.findByGoodsid(entity.getGoodsid());

                if (op.size() == 0) {
                    continue;
                }

                result.add(op.get(0));

            }
        return result;
    }

    /**
     * 清空购物车(支付成功）
     * @param tokenid
     * @param orderid
     * @throws GoodNotFoundException
     */
    @Transactional
    public void buy( String tokenid , String orderid) throws GoodNotFoundException {

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String buyer = Entity.get(0).getUserid();

        if (!v2UserService.checkUserIdExist(buyer)) {
            throw new SecondRuntimeException("用户id不存在，无法清空，请注册");
        }

       List<OrderofgoodsEntity> orderofgoodsEntities = orderofgoodDao.findByOrderid(orderid);

        for (OrderofgoodsEntity entity : orderofgoodsEntities) {

            String goodid = entity.getGoodsid();

            List<GoodOfUserEntity> temp = goodOfUserDao.findByGoodsid(goodid);
          //  List<CarEntity> temp1 = cargoodsDao.findByGoodsid(goodid);
            if (temp.size() == 0) {
                throw new GoodNotFoundException("物品id不存在", goodid);
            }


            // buyer存起来
            alreadySellDao.save(new AlreadySellEntity(Util.geFulltUniqueId(), goodid, temp.get(0).getUserid()));

            // poster存起来
            cartAlreadyBuyDao.save(new CartAlreadyBuyEntity(Util.geFulltUniqueId(), goodid, buyer));


            //商品数量为1，购买成功即商品下架
            // 购物车中删除这个商品
            cargoodsDao.deleteByGoodsid(goodid);
            //收藏列表中删除这个商品
            likeDao.deleteBygoodsid(goodid);
            //商品列表中删除这个商品
            searchDao.deleteByGoodsid(goodid);
            //删除相关评论
            commentDao.deleteByGoodsid(goodid);
        }

    }



    /**
     * 创建订单
     * @param goodIdList
     * @param tokenid
     * @param addressid
     * @return
     */
    public String createOrder(List<String> goodIdList , String tokenid , String addressid) {

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户id不存在，无法购买，请注册");
        }

        List<AdOfUserEntity> check = adOfUserDao.findByUseridAndAddressid(userid, addressid);
        if (check.size() == 0) {
            throw new SecondRuntimeException("地址选择不正确");
        }
        float sumPrice = 0;
        String string = "";
        Optional<AddressEntity> address = addressDao.findById(addressid);
        String orderid = Util.getUniqueId();

        for (String goodid : goodIdList) {

            List<GoodOfUserEntity> temp = goodOfUserDao.findByGoodsid(goodid);
            List<CarEntity> temp1 = cargoodsDao.findByUseridAndGoodsid(userid,goodid);
            List<GoodsEntity> goodentity = searchDao.findByGoodsid(goodid);
            if (temp.size() == 0) {
                throw new GoodNotFoundException("物品id不存在", goodid);
            }
            if (temp1.size() == 0) {
                throw new SecondRuntimeException("当前购物车没有此商品");

            }

            sumPrice = sumPrice+goodentity.get(0).getPrice();

            string = string+"   "+goodentity.get(0).getName();
            orderofgoodDao.save(new OrderofgoodsEntity(Util.getUniqueId(),orderid,goodid,Util.getNowTime()));

        }
         OrderEntity entity = new OrderEntity(orderid, userid, address.get().getName(), address.get().getPhone(),address.get().getProvince(),
                    address.get().getCity(),address.get().getDetail(),string,sumPrice,"待支付",Util.getNowTime());
         orderDao.save(entity);
         orderofuserDao.save(new OrderofuserEntity(Util.getUniqueId(),userid,orderid , Util.getNowTime()));

     return orderid;

    }

    /**
     * 查看所有订单
     * @return
     */
    public List<OrderEntity> findAllByDate() {
        List<OrderEntity> list = orderDao.findAll();

        list.sort((o1, o2) -> Util.compareDateFromString(o1.getTime(), o2.getTime()));

        return list;
    }

    /**
     * 查看订单（用户）
     * @param tokenid
     * @return
     */
    public List<OrderEntity> findAllByusreid(String tokenid) {

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户不存在");
        }
        List<OrderEntity> list = orderDao.findByUserid(userid);

        list.sort((o1, o2) -> Util.compareDateFromString(o1.getTime(), o2.getTime()));

        return list;
    }

    /**
     * 删除订单（管理员）
     * @param orderid
     */
    @Transactional
    public void deleteorder(String token ,String orderid) {

        List<OrderEntity> check = orderDao.findByOrderid( orderid);
        List<TokenOfmanagerEntity> temp = tokenofManagerDao.findByToken(token);
        if(temp.size() == 0){
            throw  new SecondRuntimeException("token失效");
        }
        if (check.size() == 0) {
            throw new SecondRuntimeException("订单不存在");
        }
        orderDao.deleteByOrderid(orderid);
        orderofuserDao.deleteByOrderid(orderid);
        orderofgoodDao.deleteByOrderid(orderid);


    }

    /**
     * 删除订单（用户）
     * @param tokenid
     * @param orderid
     */
    @Transactional
    public void userdeleteorder(String tokenid ,String orderid) {

        List<TokenEntity> Entity = tokenDao.findByTokenid(tokenid);
        if(Entity.size() == 0){
            throw new SecondRuntimeException("token失效");
        }
        String userid = Entity.get(0).getUserid();

        if (!v2UserService.checkUserIdExist(userid)) {
            throw new SecondRuntimeException("用户不存在");
        }

        List<OrderofuserEntity> check = orderofuserDao.findByUseridAndOrderid(userid, orderid);
        if (check.size() == 0) {
            throw new SecondRuntimeException("订单不存在");
        }
        orderDao.deleteByOrderid(orderid);
        orderofuserDao.deleteByOrderid(orderid);
        orderofgoodDao.deleteByOrderid(orderid);

    }
}