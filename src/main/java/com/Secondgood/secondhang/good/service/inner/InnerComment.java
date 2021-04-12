package com.Secondgood.secondhang.good.service.inner;


import com.Secondgood.secondhang.good.entity.GoodsEntity;

public class InnerComment {

    private InnerGood entity;

    private String content;

    public InnerComment(InnerGood entity, String content) {
        this.entity = entity;
        this.content = content;
    }

    public InnerGood getEntity() {
        return entity;
    }

    public void setEntity(InnerGood entity) {
        this.entity = entity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
