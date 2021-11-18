package com.youqin.webserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("adding resource handler:");
        var absDir = System.getProperty("user.dir");
        System.out.println(absDir);
        var location = absDir + "/"+"files/";
        System.out.println("location used to serve files:"+location);
        registry.addResourceHandler("/files/").addResourceLocations("file:" + location);
        super.addResourceHandlers(registry);
    }

}