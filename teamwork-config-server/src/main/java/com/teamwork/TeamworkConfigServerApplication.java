package com.teamwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TeamworkConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamworkConfigServerApplication.class, args);
    }
}
