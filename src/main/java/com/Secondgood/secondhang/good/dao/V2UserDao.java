package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.V2User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface V2UserDao extends JpaRepository<V2User, String> {

    List<V2User> findByEmail(String email);
    List<V2User> findByName(String name);

    List<V2User> findByEmailcode(String emailcode);

    List<V2User> findByEmailAndPassword(String email, String password);
    List<V2User> findByid(String userid);

    //List<V2User> findByid(String userid);
}
