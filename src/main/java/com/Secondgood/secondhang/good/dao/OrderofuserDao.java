package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.OrderofuserEntity;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderofuserDao extends JpaRepository<OrderofuserEntity,String> {


  List<OrderofuserEntity> findByUseridAndOrderid(String usreid ,String orderid);

    @Modifying
    void deleteByOrderid(String orderid);




}
