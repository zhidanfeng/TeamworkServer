package com.teamwork;

import com.teamwork.service.ScheduleTaskService;
import com.teamwork.vo.ScheduleTaskVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamworkScheduleApplicationTests {

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Test
    void contextLoads() {
    }

    @Test
    void addJob() {
        ScheduleTaskVO item = new ScheduleTaskVO();
        item.setLinkId(723216468493340672L);
        item.setTaskTable("task_remind_rule");
        item.setTaskCron("0 20 17 19 6 ? 2020 ");
        item.setEnabled(1);
        this.scheduleTaskService.insert(item);
    }
}
