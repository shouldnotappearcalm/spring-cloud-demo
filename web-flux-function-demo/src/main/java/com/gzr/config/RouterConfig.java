package com.gzr.config;

import com.gzr.handler.SendTimeHandler;
import com.gzr.handler.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author gaozhirong
 * @version ${todo}
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/6/12 10:17
 */
@Configuration
public class RouterConfig {

    @Autowired
    private TimeHandler timeHandler;

    @Autowired
    private SendTimeHandler sendTimeHandler;

    @Bean
    public RouterFunction<ServerResponse> timeRouter() {
        return route(GET("/time"), req -> timeHandler.getTime(req))
                .andRoute(GET("/date"), req -> timeHandler.getDate(req))
                .andRoute(GET("/times"), req -> sendTimeHandler.sendTimePerSec(req));
    }

}
