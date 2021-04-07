package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Entity(name = "orderofuser")
public class OrderofuserEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String userid;

    private String orderid;

    private String time;

    public OrderofuserEntity(String id, String userid, String orderid, String time) {
        this.id = id;
        this.userid = userid;
        this.orderid = orderid;
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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}