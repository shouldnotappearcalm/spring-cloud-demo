package com.gzr.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DcController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/consumer")
    public String dc() {
        return consumerService.consumer();
    }

    @Component
    class ConsumerService {
        @Autowired
        RestTemplate restTemplate;

        /**此注解增加服务降级方法   我的理解即为没有成功获取到服务的失败调用方法
         *
         * 在eureka-client项目，即服务提供者增加如下代码，增加延迟，重启这个项目访问将会得到fallback的返回值
         * 触发服务请求超时异常，服务消费者通过HystrixCommand注解中指定的降级逻辑进行执行，返回结果为fallback
         * Thread.sleep(5000L);
            String services = "Services: " + discoveryClient.getServices();
            System.out.println(services);
         * @return
         */
        @HystrixCommand(fallbackMethod = "fallback")
        public String consumer() {
            return restTemplate.getForObject("http://eureka-client/dc", String.class);
        }

        public String fallback() {
            return "fallback";
        }
    }

}
