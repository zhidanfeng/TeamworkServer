package com.teamwork.config;

import com.teamwork.utils.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(1,1);
    }
}
