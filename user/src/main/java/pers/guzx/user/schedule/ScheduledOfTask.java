package pers.guzx.user.schedule;

import com.alibaba.druid.support.spring.stat.SpringStatUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import pers.guzx.common.util.SpringUtil;
import pers.guzx.user.entity.SysScheduled;
import pers.guzx.user.service.SysScheduledService;
import pers.guzx.user.serviceImpl.SysScheduledServiceImpl;
import tk.mybatis.spring.mapper.SpringBootBindUtil;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/9 17:20
 * @describe
 */
public interface ScheduledOfTask extends Runnable {
    /**
     * 定时任务方法
     */
    void execute();

    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {
        SysScheduledService bean = SpringUtil.getBean(SysScheduledService.class);
        SysScheduled byScheduledKey = bean.getByScheduledKey(this.getClass().getName());
        if (0 == byScheduledKey.getStatus()) {
            return;
        }
        execute();
    }
}
