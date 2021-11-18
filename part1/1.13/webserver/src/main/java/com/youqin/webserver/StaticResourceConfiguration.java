package com.youqin.webserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("adding resource handler:");
        var absDir = System.getProperty("user.dir");
        System.out.println(absDir);
        String resourceLocation ;

        if (absDir.endsWith(File.separator )) {
             resourceLocation = "file:" + absDir + "files"+File.separator ;
        }
        else {
             resourceLocation = "file:" + absDir + File.separator + "files"+File.separator ;
        }

        System.out.println("location used to serve files ---> "+resourceLocation);
        registry.addResourceHandler("/files/**").addResourceLocations(resourceLocation);
        super.addResourceHandlers(registry);
    }

}