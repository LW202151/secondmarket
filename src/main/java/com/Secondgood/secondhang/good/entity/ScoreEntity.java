package com.Secondgood.secondhang.good.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "score")
@NoArgsConstructor
public class ScoreEntity {

    @Id
    private String id;

    private String userid;

    private String tag;

    private Integer score;

    private String time;

    public ScoreEntity(String id, String userid, String tag, Integer score, String time) {
        this.id = id;
        this.userid = userid;
        this.tag = tag;
        this.score = score;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
