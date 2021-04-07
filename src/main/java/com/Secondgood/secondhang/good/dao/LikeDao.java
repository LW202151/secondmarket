package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.GoodsEntity;
import com.Secondgood.secondhang.good.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeDao extends JpaRepository <LikeEntity, Integer> {
   @Modifying
   void deleteBygoodsid(String goodsid);


   List<LikeEntity> findByUserid(String userid);
   List<LikeEntity> findByUseridAndGoodsid(String userid , String goodsid);
   //List<LikeEntity> getAllLike(String userId);
 //  List<LikeEntity> findBygoodsid(String goodsid);
}




