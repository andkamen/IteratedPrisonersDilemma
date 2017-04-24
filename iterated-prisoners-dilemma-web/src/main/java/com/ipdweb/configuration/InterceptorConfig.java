package com.ipdweb.configuration;

import com.ipdweb.areas.common.interceptors.ContentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    private ContentValidator contentValidator;

    @Autowired
    public InterceptorConfig(ContentValidator contentValidator) {
        this.contentValidator = contentValidator;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(contentValidator).addPathPatterns("/tournaments/**").addPathPatterns("/simulations/**");
    }
}
