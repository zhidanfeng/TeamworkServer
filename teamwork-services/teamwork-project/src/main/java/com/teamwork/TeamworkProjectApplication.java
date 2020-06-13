package com.teamwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TeamworkProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamworkProjectApplication.class, args);
    }

}
