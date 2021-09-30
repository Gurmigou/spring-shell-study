package com.yehorbukh.springshell.config;

import com.yehorbukh.springshell.filters.SecondFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean<SecondFilter> loggingFilter() {
        var registrationBean = new FilterRegistrationBean<SecondFilter>();

        registrationBean.setFilter(new SecondFilter());
        registrationBean.addUrlPatterns("/api/v1/weather/rain/*");

        return registrationBean;
    }
}
