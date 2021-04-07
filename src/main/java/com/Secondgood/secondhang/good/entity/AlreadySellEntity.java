package com.Secondgood.secondhang.good.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "alreadysell")
public class AlreadySellEntity  {


    @Id
    private String id;

    private String goodsid;

    private String userid;

    public AlreadySellEntity() {
    }

    public AlreadySellEntity(String id, String goodsid, String userid) {
        this.id = id;
        this.goodsid = goodsid;
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
