package com.gzr.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 创建一个Feign客户端接口定义，使用@FeignClient注解来指定这个接口所要调用的服务的名称
 */
@FeignClient("eureka-client")
public interface DcClient {

    /**
     * 使用这个注解绑定服务提供方的REST接口 这个get方法绑定 /dc接口
     * @return
     */
    @GetMapping("/dc")
    String consumer();

}
