package pers.guzx.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pers.guzx.user.entity.SysScheduled;
import pers.guzx.user.mapper.SysScheduledMapper;
import pers.guzx.user.schedule.ScheduledOfTask;
import pers.guzx.user.service.SysScheduledService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/9 16:13
 * @describe
 */
@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Resource
    private SysScheduledService scheduledService;
    @Resource
    private ApplicationContext context;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (SysScheduled springScheduledCron : scheduledService.getAll()) {
            Class<?> clazz;
            Object task;
            try {
                clazz = Class.forName(springScheduledCron.getCronKey());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("spring_scheduled_cron表数据" + springScheduledCron.getCronKey() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(springScheduledCron.getCronKey() + "未纳入到spring管理", e);
            }
            Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
            // 可以通过改变数据库数据进而实现动态改变执行周期
            taskRegistrar.addTriggerTask(((Runnable) task),
                    triggerContext -> {
                        String cronExpression = scheduledService.getByScheduledKey(springScheduledCron.getCronKey()).getCronExpression();
                        return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
                    }
            );
        }
    }
}
