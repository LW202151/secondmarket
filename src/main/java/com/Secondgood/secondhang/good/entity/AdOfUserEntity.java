package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "adofuser")
@NoArgsConstructor
public class AdOfUserEntity {

    @Id
    private String id;

    private String userid;

    private String addressid;

    public AdOfUserEntity(String id, String userid, String addressid) {
        this.id = id;
        this.userid = userid;
        this.addressid = addressid;
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

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }
}