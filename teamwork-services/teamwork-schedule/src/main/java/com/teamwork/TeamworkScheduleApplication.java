package com.teamwork;

import com.teamwork.service.ScheduleTaskService;
import com.teamwork.service.impl.ScheduleTaskServiceImpl;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class TeamworkScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamworkScheduleApplication.class, args);
    }
}
