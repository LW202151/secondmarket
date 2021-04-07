package com.Secondgood.secondhang.good.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "address")
@NoArgsConstructor
public class AddressEntity  {

  @Id
  private String id;

  private String userid;

  private String name;

  private String phone;

  private String province;

  private String city;

  private String detail;

    public AddressEntity(String id, String userid, String name, String phone, String province, String city, String detail) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.detail = detail;
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
}
