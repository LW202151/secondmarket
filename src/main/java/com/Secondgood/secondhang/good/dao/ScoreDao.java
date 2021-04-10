package com.Secondgood.secondhang.good.dao;

import com.Secondgood.secondhang.good.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreDao extends JpaRepository<ScoreEntity,String> {

    /**
     * score降序
     * @param userid
     * @return
     */
    List<ScoreEntity> findByUseridOrderByScoreDesc(String userid);

    List<ScoreEntity> findByUseridAndTag(String userid , String tag);

    List<ScoreEntity> findByScore(int score);

}
