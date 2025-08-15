package com.app.task_api.config;

import com.app.task_api.filters.CsrfTokenFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CsrfTokenFilter> csrfFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        FilterRegistrationBean<CsrfTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CsrfTokenFilter(resolver));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
