package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.OrderofgoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderofgoodDao extends JpaRepository<OrderofgoodsEntity,String> {

    @Modifying
    void deleteByOrderid(String orderid);

    List<OrderofgoodsEntity> findByOrderid(String orderid);
    List<OrderofgoodsEntity> findByGoodsid(String goodsid);






}
