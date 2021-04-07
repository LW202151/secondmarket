package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_id")
    private Integer cid;

    private String userid;

    private String goodsid;


    public CarEntity(String userid, String goodsid) {
        this.userid = userid;
        this.goodsid = goodsid;

    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }


}
