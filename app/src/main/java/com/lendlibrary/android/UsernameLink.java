package com.lendlibrary.android;

public class UsernameLink {
    public String email,username,phone,uid;

    public UsernameLink() {
    }

    public UsernameLink(String email, String username, String phone,String uid) {
        this.email = email;
        this.username=username;
        this.phone=phone;
        this.uid=uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone(){return phone;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
