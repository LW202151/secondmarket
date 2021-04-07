package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "order_main")
//@Table(name = "order_main")
public class OrderEntity {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderid;

    private String userid;

    @Column(name = "`name`", columnDefinition = "varchar(255)")
    private String name;

    private String phone;

    private String province;

    private String city;

    private String detail;

    private String goodname;

    private Float price;

    private String state;

    private String time;


    public OrderEntity(String orderid, String userid, String name, String phone, String province, String city, String detail, String goodname, Float price, String state, String time) {
        this.orderid = orderid;
        this.userid = userid;
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.detail = detail;
        this.goodname = goodname;
        this.price = price;
        this.state = state;
        this.time = time;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
