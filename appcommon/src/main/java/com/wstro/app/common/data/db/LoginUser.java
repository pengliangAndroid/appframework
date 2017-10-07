package com.wstro.app.common.data.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 登录用户表
 * Created by pengl on 2016/12/2.
 */
@Entity
public class LoginUser {
    @Id
    private Long id;

    @Property
    private String username;

    @Property
    private String password;

    @Property
    private Long lastLoginTime;

    @Generated(hash = 1051892345)
    public LoginUser(Long id, String username, String password, Long lastLoginTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
    }

    @Generated(hash = 1159929338)
    public LoginUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        LoginUser user;
        if(obj instanceof LoginUser) {
            user = (LoginUser) obj;
        }else{
            return false;
        }

        return username.equals(user.getUsername());
    }
}
