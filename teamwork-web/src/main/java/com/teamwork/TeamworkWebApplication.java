package com.teamwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.teamwork")
public class TeamworkWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamworkWebApplication.class, args);
    }

}
