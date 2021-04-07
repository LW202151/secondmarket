package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressDao extends JpaRepository<AddressEntity, String> {

    List<AddressEntity> findByUserid(String userid);
    Optional<AddressEntity> findById(String addressid);
    List<AddressEntity> findByUseridAndId(String userid,String addressid);

    @Modifying
    void deleteById(String addressid);

}
