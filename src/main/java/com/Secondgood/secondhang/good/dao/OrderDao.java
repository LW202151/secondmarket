package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity,String> {

   List<OrderEntity> findByUserid(String userid);

   List<OrderEntity> findByOrderid(String orderid);

   @Modifying
   void deleteByOrderid(String orderid);

   @Modifying
   void deleteByUseridAndOrderid (String userid , String orderid);


}
