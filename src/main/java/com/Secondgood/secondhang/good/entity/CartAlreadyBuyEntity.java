package com.Secondgood.secondhang.good.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Entity(name = "cartalreadybuy")
public class CartAlreadyBuyEntity  {

    @Id
    private String id;

    private String goodsid;

    private String userid;

    public CartAlreadyBuyEntity(String id, String goodsid, String userid) {
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
