package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.SecondcodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecondcodeDao extends JpaRepository<SecondcodeEntity,String> {

   //List<SecondcodeEntity> findByContent(String content);
}
