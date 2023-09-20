package com.sap.cc.greetings;

public class Greeting {
    private final String template;

    public Greeting(String template) {
        this.template = template;
    }

    public String forName(String world) {
        return String.format(template, world);
    }
}
