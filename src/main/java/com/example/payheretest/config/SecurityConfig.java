package com.example.payheretest.config;

import com.example.payheretest.security.JwtTokenFilter;
import com.example.payheretest.security.JwtTokenProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean someFilterRegistration(JwtTokenProvider jwtTokenProvider) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtTokenFilter(jwtTokenProvider));
        filterRegistrationBean.addUrlPatterns("/api/v1/*");
        filterRegistrationBean.addUrlPatterns("/auth/user/*");
        return filterRegistrationBean;
    }
}