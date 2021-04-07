package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.CarEntity;
import com.Secondgood.secondhang.good.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao extends JpaRepository<CarEntity, Integer> {
    void deleteBygoodsid(String goodsid);

    List<CarEntity> findByUserid(String userid);

    List<CarEntity>  findByUseridAndGoodsid(String userid , String goodsid);

    List<CarEntity>  findByGoodsid( String goodsid);


    @Modifying
    void deleteByGoodsid(String goodsid);



}
