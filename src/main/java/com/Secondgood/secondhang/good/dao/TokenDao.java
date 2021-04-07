package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenDao extends JpaRepository<TokenEntity ,String> {

    List<TokenEntity> findByTokenid(String tokenid);
    List<TokenEntity> findByUserid(String userid);

    @Modifying
    void deleteByTokenid(String tokenid);





}
