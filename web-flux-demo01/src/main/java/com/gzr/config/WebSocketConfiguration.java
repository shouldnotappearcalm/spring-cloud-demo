package com.gzr.config;

import com.gzr.controller.WsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaozhirong
 * @version ${todo}
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/6/8 17:36
 */
@Component
public class WebSocketConfiguration {

    /**
     * 在创建了 WebSocket 的处理器 EchoHandler 之后，下一步需要把它注册到 WebFlux 中。我们首先需要创建一个类 WebSocketHandlerAdapter 的对象，
     * 该对象负责把 WebSocketHandler 关联到 WebFlux 中。代码清单 7 中给出了相应的 Spring 配置。其中的 HandlerMapping 类型的 bean 把 EchoHandler 映射到路径 /echo。
     *
     * @param wsServer
     * @return
     */
    @Autowired
    @Bean
    public HandlerMapping webSocketMapping(final WsServer wsServer) {
        final Map<String, WebSocketHandler> map = new HashMap<>(1);
        map.put("echo", wsServer);

        final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        mapping.setUrlMap(map);
        return mapping;
    }

    //return new WsServer()
    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

}
