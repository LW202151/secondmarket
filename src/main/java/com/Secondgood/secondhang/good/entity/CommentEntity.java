package com.Secondgood.secondhang.good.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "comment")
@NoArgsConstructor
public class CommentEntity {

   @Id
   private String commentid;

   private String userid;
    private String goodsid;

    private String content;

    private String time;



    public String getCommentid() {
        return commentid;
    }

    public CommentEntity(String commentid, String userid, String goodsid, String content, String time) {
        this.commentid = commentid;
        this.userid = userid;
        this.goodsid = goodsid;
        this.content = content;
        this.time = time;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
