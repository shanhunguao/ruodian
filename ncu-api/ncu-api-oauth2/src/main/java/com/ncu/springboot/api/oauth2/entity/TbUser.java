package com.ncu.springboot.api.oauth2.entity;

/**
 * @Created by zhoufan
 * @Date 2020/3/30 15:46
 */

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_user")
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "user_code")
    private String username;

    /**
     * 密码，加密存储
     */
    @Column(name = "`password`")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}