package com.youqin.webserver;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Component
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
// Registered Interceptor
        System.out.println("ADD INTERCEPTOR");
        InterceptorRegistration ir = registry.addInterceptor(new DLPictureInterceptor());
        // Configuring Interception Path
        ir.addPathPatterns("/**");
        // Configure non-intercepting paths
        ir.excludePathPatterns("/**.html");
    }

    class DLPictureInterceptor implements HandlerInterceptor {

        String directory = "./files";
        String picturePath = directory+"/picture.jpg";
        String pictureUrl = "https://picsum.photos/1200";

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            System.out.println("INTERCEPT:"+request.getRequestURI());
            downloadPictureIfNeeded();
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        private void downloadPictureIfNeeded() throws IOException {
            System.out.println("downloadPictureIfNeeded()");
            Files.createDirectories(Paths.get(directory));

            File pictureFile = new File(picturePath);
            boolean exists = pictureFile.exists();
            boolean shouldDownloadImage = !exists || ( (new Date().getTime() - pictureFile.lastModified()) >= 24 *60*60 *1000);
            System.out.println("should download="+shouldDownloadImage);
            if (shouldDownloadImage) {
                pictureFile.setLastModified(new Date().getTime());
                FileUtils.copyURLToFile(
                        new URL(pictureUrl),
                        pictureFile,
                        5000,
                        5000);
            }
        }
    }
}