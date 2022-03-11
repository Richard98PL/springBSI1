package com.bp.springmvc.beans;

public class User {
    private int id;
    private String login;
    private String password;
    private String salt;
    private Boolean isPasswordKeptAsHash;
    
    private String passwordHash;

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getIsPasswordKeptAsHash() {
        return isPasswordKeptAsHash;
    }

    public void setIsPasswordKeptAsHash(Boolean isPasswordKeptAsHash) {
        this.isPasswordKeptAsHash = isPasswordKeptAsHash;
    }
}