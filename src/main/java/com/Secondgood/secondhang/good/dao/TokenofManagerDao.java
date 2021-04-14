package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.TokenOfmanagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenofManagerDao extends JpaRepository<TokenOfmanagerEntity,String> {

    @Modifying
    void deleteByToken(String token);

    List<TokenOfmanagerEntity> findByToken(String token);


}
