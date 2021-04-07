package com.Secondgood.secondhang.good.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Entity(name = "goodofuser")
public class GoodOfUserEntity {

    @Id
    private String goodsid;

    private String userid;


    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public GoodOfUserEntity(String goodsid, String userid) {
        this.goodsid = goodsid;
        this.userid = userid;
    }
}
