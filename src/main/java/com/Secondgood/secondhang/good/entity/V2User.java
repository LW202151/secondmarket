package com.Secondgood.secondhang.good.entity;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "v2user")
@NoArgsConstructor
public class V2User {

    @Id
    private String id;

    private String email;

    private String name;

    private String password;

    private String emailcode;

    public V2User(String id, String email, String name, String password, String emailcode) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.emailcode = emailcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailcode() {
        return emailcode;
    }

    public void setEmailcode(String emailcode) {
        this.emailcode = emailcode;
    }
}
