package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerDao extends JpaRepository<ManagerEntity,String> {


    List<ManagerEntity> findByName(String name);

    List<ManagerEntity> findByPassword(String password);

}
