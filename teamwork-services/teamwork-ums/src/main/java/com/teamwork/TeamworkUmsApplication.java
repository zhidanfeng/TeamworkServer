package com.teamwork;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class TeamworkUmsApplication {

    @Value("${pagehelper.reasonable}")
    private String test;

    public static void main(String[] args) {
        SpringApplication.run(TeamworkUmsApplication.class, args);
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return test;
    }
}
