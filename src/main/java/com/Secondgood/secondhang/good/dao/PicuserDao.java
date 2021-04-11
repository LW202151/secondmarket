package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.PicuserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PicuserDao extends JpaRepository<PicuserEntity ,String> {

    List<PicuserEntity> findByUserid(String userid);




}
