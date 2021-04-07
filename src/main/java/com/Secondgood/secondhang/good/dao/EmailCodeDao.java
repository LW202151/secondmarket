package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.CartAlreadyBuyEntity;
import com.Secondgood.secondhang.good.entity.EmailCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailCodeDao extends JpaRepository<EmailCodeEntity, String> {

    List<EmailCodeEntity> findByCode(String code);
    List<EmailCodeEntity> findByCodeAndEmail(String code, String email);


}
