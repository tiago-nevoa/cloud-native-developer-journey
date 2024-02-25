package com.sap.cc.user;

public class User {

    String locale, name;

    public User(String name, String locale) {
        this.name = name;
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "locale='" + locale + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
