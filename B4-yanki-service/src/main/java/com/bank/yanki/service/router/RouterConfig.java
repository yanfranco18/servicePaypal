package com.bank.yanki.service.router;

import com.bank.yanki.service.handler.YankiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes (YankiHandler handler) {
        return route(GET("/yankis"), handler::findAll)
                .andRoute(GET("/yankis/{id}"), handler::findId)
                .andRoute(GET("/yankis/phone/{phoneNumber}"), handler::findByPhoneNumber)
                .andRoute(POST("/yankis"),handler::create)
                .andRoute(DELETE("/yankis/{id}"), handler::deleteById);
    }
}
