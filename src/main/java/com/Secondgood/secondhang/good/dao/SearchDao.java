package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.GoodPK;
import com.Secondgood.secondhang.good.entity.GoodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface SearchDao extends JpaRepository<GoodsEntity, GoodPK> {

    @Query(value="select * from goods  where Name like CONCAT('%',:Name,'%')",nativeQuery=true)
    List<GoodsEntity> findByName(@Param("Name") String Name);

    @Query(value="select * from goods  where Tag like CONCAT('%',:Tag,'%')",nativeQuery=true)
    List<GoodsEntity> findByTag(@Param("Tag") String Tag);

    @Query(value="select * from goods  where Type like CONCAT('%',:Type,'%')",nativeQuery=true)
    List<GoodsEntity> findByType(@Param("Type") String Type);


    List<GoodsEntity> findByGoodsid(String Goodsid);
    //List<GoodsEntity> findByG_id(Integer g_id);
    @Modifying
        void deleteByGoodsid(String GoodsID);

}