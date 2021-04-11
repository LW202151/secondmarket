package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "picuser")
@NoArgsConstructor
public class PicuserEntity {

    @Id
    private String id ;

    private String userid;

    private String pickey;

    public PicuserEntity(String id, String userid, String pickey) {
        this.id = id;
        this.userid = userid;
        this.pickey = pickey;
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

    public String getPickey() {
        return pickey;
    }

    public void setPickey(String pickey) {
        this.pickey = pickey;
    }
}
