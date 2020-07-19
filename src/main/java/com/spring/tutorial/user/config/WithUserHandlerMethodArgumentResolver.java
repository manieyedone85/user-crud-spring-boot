package com.spring.tutorial.user.config;

import com.spring.tutorial.user.common.Constants;
import com.spring.tutorial.user.common.WithUser;
import com.spring.tutorial.user.exception.ApiException;
import com.spring.tutorial.user.persistence.repository.UserRepository;
import com.spring.tutorial.user.persistence.schema.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class WithUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // We check if our parameter is exactly what we need:
        return parameter.hasParameterAnnotation(WithUser.class) &&
                parameter.getParameterType().equals(User.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter methodParameter, BindingContext bindingContext, ServerWebExchange serverWebExchange) {
        //take the request
        ServerHttpRequest request = serverWebExchange.getRequest();
        //take our userId header from request and find the user from db
        String userId = request.getHeaders().getFirst(Constants.Header.USER_ID);
        if (userId != null) {
            log.info("Trying to find the user using userId = {}", userId);

            return userRepository.findById(userId)
                    .flatMap(this::getMono)
                    .switchIfEmpty(Mono.error(new ApiException("Invalid user.", HttpStatus.BAD_REQUEST, "400", "Invalid user."))
                    );
        } else {
            log.error("UNAUTHORIZED Request received");
            return Mono.error(new ApiException("Unauthorized user.", HttpStatus.UNAUTHORIZED, "401", "Unauthorized user."));
        }
    }

    private Mono<Object> getMono(User user) {
        return Mono.just(user);
    }
}