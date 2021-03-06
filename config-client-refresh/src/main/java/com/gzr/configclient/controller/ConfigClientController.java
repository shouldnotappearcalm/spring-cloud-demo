package com.gzr.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${profile}")
    private String profile;

    @Value("${testPro}")
    private String testProp;

    @GetMapping("/profile")
    public String hello() {
        return this.profile;
    }

    @GetMapping("/testProp")
    public String testProp() {
        return this.testProp;
    }
}
