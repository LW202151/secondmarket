package com.Secondgood.secondhang.good.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "secondcode")
public class SecondcodeEntity {

    @Id
    private String id;

    private Integer count;

    private String content;

    public SecondcodeEntity(String id, Integer count, String content) {
        this.id = id;
        this.count = count;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
