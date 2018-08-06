package com.hackzone.concurrencymc.example.config;

import com.hackzone.concurrencymc.example.Filter.HttpFilter;
import com.hackzone.concurrencymc.example.interceptor.HttpInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean httpFilter() {
        FilterRegistrationBean<HttpFilter> httpFilterRegBean = new FilterRegistrationBean<>();
        httpFilterRegBean.setFilter(new HttpFilter());
        httpFilterRegBean.addUrlPatterns("/threadLocal/*");
        return httpFilterRegBean;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**");
    }
}
