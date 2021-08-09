package pers.guzx.user.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/9 17:33
 * @describe
 */
@Slf4j
@Component
public class DynamicPrintTask2 implements ScheduledOfTask {
    @Override
    public void execute() {
        log.info("定时任务222执行了");
    }
}
