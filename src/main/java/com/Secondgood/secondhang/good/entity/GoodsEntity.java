package com.Secondgood.secondhang.good.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.omg.CORBA.StringHolder;

import javax.persistence.*;
import java.sql.Timestamp;


@IdClass(GoodPK.class)
@Entity(name = "goods")
@NoArgsConstructor
public class GoodsEntity {
    @Id
    private String goodsid;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer g_id;


    private String name;

    private String tag;

    private String desciption;

    private Float price;

    private String type;

    private String updatedate;


    public GoodsEntity(String goodsid, String name, String tag, String type, Float price, String desciption, String updatedate) {
        this.goodsid = goodsid;
        this.name = name;
        this.tag = tag;
        this.desciption = desciption;
        this.price = price;
        this.type = type;
        this.updatedate = updatedate;

    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public void setG_id(Integer g_id) {
        this.g_id = g_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }


    public String getGoodsid() {
        return goodsid;
    }

    public Integer getG_id() {
        return g_id;
    }

    public String getName() {
        return name;
    }

    public String getTag(String tag) {
        return this.tag;
    }

    public String getDesciption() {
        return desciption;
    }

    public Float getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getUpdatedate() {
        return updatedate;
    }
}
