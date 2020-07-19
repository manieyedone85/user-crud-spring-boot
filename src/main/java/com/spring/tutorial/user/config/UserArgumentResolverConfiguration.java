package com.spring.tutorial.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
public class UserArgumentResolverConfiguration implements WebFluxConfigurer {
    @Autowired
    WithUserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver;

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(userHandlerMethodArgumentResolver);
    }
}