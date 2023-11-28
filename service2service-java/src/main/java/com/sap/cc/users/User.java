package com.sap.cc.users;

public class User {

    private Long id;


    private String name;
    private String phoneNumber;

    private String fontPreference;

    public User(String name, String phoneNumber, String fontPreference) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fontPreference = fontPreference;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fontPreference='" + fontPreference + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFontPreference() {
        return fontPreference;
    }

    public void setFontPreference(String fontPreference) {
        this.fontPreference = fontPreference;
    }
}
