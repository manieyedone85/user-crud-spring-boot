package com.spring.tutorial.user;

import com.spring.tutorial.user.config.WithUserHandlerMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class UserCRUDApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCRUDApplication.class, args);
        log.info("Starting tutorial user-crud services...");
    }

    @Bean
    public WithUserHandlerMethodArgumentResolver getWithUserHandlerMethodArgumentResolver() {
        return new WithUserHandlerMethodArgumentResolver();
    }
}
