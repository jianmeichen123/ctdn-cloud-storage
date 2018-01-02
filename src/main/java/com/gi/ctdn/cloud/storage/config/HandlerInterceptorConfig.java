package com.gi.ctdn.cloud.storage.config;

import com.gi.ctdn.cloud.storage.rest.UploadInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class HandlerInterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UploadInterceptor uploadInterceptor() {
        return new UploadInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(uploadInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
