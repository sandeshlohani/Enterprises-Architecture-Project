package com.project.eaproject.config;

import feign.RequestInterceptor;
import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class MyClientConfig implements HandlerInterceptor {

    static String authorization = "";

    @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        authorization = request.getHeader("Authorization");
        return true;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", authorization);
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
    }

}
