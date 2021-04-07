package com.Secondgood.secondhang.good.service.inner;

import com.Secondgood.secondhang.good.entity.GoodsEntity;
import com.Secondgood.secondhang.good.entity.LikeEntity;
import com.Secondgood.secondhang.good.entity.V2User;

public class InnerUser {

    private V2User entity ;

    private String tokenid;

    public InnerUser(V2User entity, String tokenid) {
        this.entity = entity;
        this.tokenid = tokenid;
    }

    public V2User getEntity() {
        return entity;
    }

    public void setEntity(V2User entity) {
        this.entity = entity;
    }

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }
}