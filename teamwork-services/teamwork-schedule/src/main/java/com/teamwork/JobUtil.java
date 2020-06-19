package com.teamwork;

import com.teamwork.job.TaskRemindJob;
import com.teamwork.job.TestJob;
import com.teamwork.vo.ScheduleTaskVO;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobUtil {

    private static final String TASK_REMIND_SCHEDULE = "task_remind_rule";

    @Autowired
    private Scheduler scheduler;

    public void addJob(ScheduleTaskVO jobInfo) throws SchedulerException {

        if(!CronExpression.isValidExpression(jobInfo.getTaskCron())) {
            System.out.println("表达式不正确");
        }

        JobDetail jobDetail = null;
        if(TASK_REMIND_SCHEDULE.equals(jobInfo.getTaskTable())) {
            jobDetail = JobBuilder.newJob(TaskRemindJob.class).build();

            //表达式调度构建器(即任务执行的时间,不立即执行)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getTaskCron()).withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().startAt(new Date())
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            jobDetail = JobBuilder.newJob(TestJob.class).build();

            //创建触发器 每3秒钟执行一次
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group3")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        }
    }
}
