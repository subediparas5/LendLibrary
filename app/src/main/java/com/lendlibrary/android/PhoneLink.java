package com.lendlibrary.android;

public class PhoneLink {
    public String phone,email;

    public PhoneLink(String email) {
        this.email = email;
    }

    public PhoneLink() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
