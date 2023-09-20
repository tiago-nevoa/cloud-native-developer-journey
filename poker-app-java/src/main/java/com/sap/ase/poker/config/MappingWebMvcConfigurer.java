package com.sap.ase.poker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MappingWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/table");
        registry.addRedirectViewController("/table", "/table/");
        registry.addRedirectViewController("/table/", "/table/index.html");

        registry.addRedirectViewController("/login", "/login/");
        registry.addRedirectViewController("/login/", "/login/index.html");
    }
}
