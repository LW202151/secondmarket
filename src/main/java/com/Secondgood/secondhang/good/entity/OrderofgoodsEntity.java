package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Entity(name = "orderofgood")
public class OrderofgoodsEntity {

    @Id
    private String id;

    private String orderid;

    private String goodsid;

    private String time;

    public OrderofgoodsEntity(String id, String orderid, String goodsid, String time) {
        this.id = id;
        this.orderid = orderid;
        this.goodsid = goodsid;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
