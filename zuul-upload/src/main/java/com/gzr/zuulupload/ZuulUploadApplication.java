package com.gzr.zuulupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ZuulUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulUploadApplication.class, args);
    }
}
