package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.AlreadySellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlreadySellDao extends JpaRepository<AlreadySellEntity, String> {

    List<AlreadySellEntity> findByUserid(String userid);

    @Modifying
    void deleteByGoodsid(String goodsid);
}
