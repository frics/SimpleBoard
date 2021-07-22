package com.example.springbootexample.configuration;

import com.example.springbootexample.interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private static final List<String> URL_PATTERNS = Arrays.asList("/board/*", "/board");
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoggerInterceptor loggerInterceptor = new LoggerInterceptor();
        registry.addInterceptor((loggerInterceptor)).addPathPatterns(URL_PATTERNS);
    }
}
