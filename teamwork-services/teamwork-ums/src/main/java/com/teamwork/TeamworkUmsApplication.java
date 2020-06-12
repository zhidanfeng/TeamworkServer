package com.teamwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TeamworkUmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamworkUmsApplication.class, args);
    }

}
