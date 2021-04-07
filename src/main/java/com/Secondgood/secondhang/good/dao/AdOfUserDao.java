package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.AdOfUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdOfUserDao extends JpaRepository<AdOfUserEntity, String> {

    List<AdOfUserEntity> findByUserid(String userid);

    List<AdOfUserEntity> findByUseridAndAddressid(String userid ,String addressid);

    @Modifying
    void deleteByAddressid(String addressid);
}
