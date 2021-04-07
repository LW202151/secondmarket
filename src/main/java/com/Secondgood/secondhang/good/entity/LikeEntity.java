package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.*;

//@IdClass(GoodPK.class)
@NoArgsConstructor
@Entity(name = "liketable")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "l_id")
    private Integer lid;

    private String userid;

    private String goodsid;

    private String likedate;

    public LikeEntity(String userid, String goodsid, String likedate) {
        this.userid = userid;
        this.goodsid = goodsid;
        this.likedate = likedate;
    }

    public Integer getLid() {
        return lid;
    }

    public String getUserid() {
        return userid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public String getLikedate() {
        return likedate;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public void setLikedate(String likedate) {
        this.likedate = likedate;
    }
}