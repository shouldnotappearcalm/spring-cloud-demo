package com.gzr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author gaozhirong
 * @version ${todo}
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/6/89:27
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/hello_world")
    public Mono<String> sayHelloWorld(){
        return Mono.just("Hello World");
    }

}
