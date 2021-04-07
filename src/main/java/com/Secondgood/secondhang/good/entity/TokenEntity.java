package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "token")
@NoArgsConstructor
public class TokenEntity {

    @Id
    private String userid;

    private String tokenid;

    public TokenEntity(String userid, String tokenid) {
        this.userid = userid;
        this.tokenid = tokenid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }
}
