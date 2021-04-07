package com.Secondgood.secondhang.good.service.inner;

import com.Secondgood.secondhang.good.entity.GoodsEntity;
import com.Secondgood.secondhang.good.entity.LikeEntity;
import lombok.Getter;
import lombok.Setter;


public class InnerGood {
   private GoodsEntity entity;

    // 图片真实地址
    private String url;

    public GoodsEntity getEntity() {
        return entity;
    }

    public InnerGood(GoodsEntity entity, String url) {
        this.entity = entity;
        this.url = url;
    }

    public void setEntity(GoodsEntity entity) {
        this.entity = entity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



}
