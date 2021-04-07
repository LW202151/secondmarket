package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.PicGoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PicGoodDao extends JpaRepository<PicGoodEntity, String> {
    List<PicGoodEntity> findByGoodsid(String goodid);

}
