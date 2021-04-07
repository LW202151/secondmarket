package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.CartAlreadyBuyEntity;
import com.Secondgood.secondhang.good.entity.GoodOfUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartAlreadyBuyDao extends JpaRepository<CartAlreadyBuyEntity, String> {

    List<CartAlreadyBuyEntity> findByUserid(String userId);

    List<CartAlreadyBuyEntity> findByUseridAndGoodsid(String usreid, String goodsid);
   // List<CartAlreadyBuyEntity> findByGoodID(String GoodId);
   @Modifying
   void deleteByGoodsid(String goodsid);
}
