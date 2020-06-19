package com.teamwork;

import com.teamwork.service.ScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 服务正式启动完成后启动定时任务
 */
@Component
public class StartQuartzListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        scheduleTaskService.selectAll();
    }
}
