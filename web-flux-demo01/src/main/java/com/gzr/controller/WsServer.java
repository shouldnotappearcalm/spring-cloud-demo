package com.gzr.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author gaozhirong
 * @version ${todo}
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/6/8 16:47
 */
@Component
public class WsServer implements WebSocketHandler {


    /**
     * 对于每个接收到的消息，会发送一个添加了 Echo前缀的响应消息
     * receive返回值是一个Flux<WebSocketMessage>对象，表示的是接收到的消息流
     * send方法参数是一个Publisher<WebSocketMessage>对象，表示要发送的消息流
     * map操作对receive方法得到的Flux<WebSocketMessage>中包含的消息继续处理
     * @param webSocketSession
     * @return
     */
    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(
                webSocketSession.receive()
                        .map(msg -> webSocketSession.textMessage("Echo -> " + msg.getPayloadAsText())));
    }
}
