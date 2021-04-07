package com.Secondgood.secondhang.good.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "picgood")

public class PicGoodEntity {

    @Id
    private String id;

    private String goodsid;

    private String pickey;

    public PicGoodEntity(String id, String goodsid, String pickey) {
        this.id = id;
        this.goodsid = goodsid;
        this.pickey = pickey;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public void setPickey(String pickey) {
        this.pickey = pickey;
    }

    public PicGoodEntity() {
    }

    public String getId() {
        return id;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public String getPickey() {
        return pickey;
    }
}
