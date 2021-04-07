package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.GoodOfUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GoodOfUserDao  extends JpaRepository<GoodOfUserEntity, String> {

    List<GoodOfUserEntity> findByGoodsid(String goodId);

    List<GoodOfUserEntity> findByUserid(String userid);

    List<GoodOfUserEntity> findByGoodsidAndUserid(String goodsid, String userid);


    @Modifying
    void deleteByGoodsid(String goodsid);
}
