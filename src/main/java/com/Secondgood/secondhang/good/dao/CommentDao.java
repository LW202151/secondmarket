package com.Secondgood.secondhang.good.dao;


import com.Secondgood.secondhang.good.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<CommentEntity,String> {

    List<CommentEntity> findByUserid(String userid);
    List<CommentEntity> findByGoodsid(String goodsid);
    List<CommentEntity> findByCommentid(String commentid);
    List<CommentEntity> findByUseridAndCommentid(String userid , String commentid);
    List<CommentEntity> findByUseridAndGoodsid(String userid , String goodsid);

    @Modifying
    void  deleteByCommentid(String commentid);

    @Modifying
    void deleteByGoodsid(String goodsid);




}
