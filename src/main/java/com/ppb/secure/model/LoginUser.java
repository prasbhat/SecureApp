package com.ppb.secure.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int systemLoginUserId;

    private String username;
    private String password;
    private String keyGen;

    public int getSystemLoginUserId() {
        return systemLoginUserId;
    }

    public void setSystemLoginUserId(int systemLoginUserId) {
        this.systemLoginUserId = systemLoginUserId;
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

    public String getKeyGen() {
        return keyGen;
    }

    public void setKeyGen(String keyGen) {
        this.keyGen = keyGen;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "systemLoginUserId=" + systemLoginUserId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", keyGen='" + keyGen + '\'' +
                '}';
    }
}
