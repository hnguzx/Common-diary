package pers.guzx.demo.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import pers.guzx.demo.entity.DynamicDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/19 14:47
 * @describe
 */
@Slf4j
@Component
public class DataSourceRefresher implements ApplicationContextAware {
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Resource
    private DynamicDataSource dynamicDataSource;

    @Resource
    private DataSourceManager dataSourceManager;

    @Resource
    private ApplicationContext applicationContext;

    @ApolloConfigChangeListener(interestedKeyPrefixes = "spring.datasource.")
    public void onChange(ConfigChangeEvent changeEvent) {
        refreshDataSource(changeEvent.changedKeys());
    }

    private synchronized void refreshDataSource(Set<String> changedKeys) {
        try {
            log.info("Refreshing data source");

            /**
             * rebind configuration beans, e.g. DataSourceProperties
             * @see org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder#onApplicationEvent
             */
            this.applicationContext.publishEvent(new EnvironmentChangeEvent(changedKeys));

            DataSource newDataSource = dataSourceManager.createAndTestDataSource();
            DataSource oldDataSource = dynamicDataSource.setDataSource(newDataSource);
            asyncTerminate(oldDataSource);

            log.info("Finished refreshing data source");
        } catch (Throwable ex) {
            log.error("Refreshing data source failed", ex);
        }
    }

    private void asyncTerminate(DataSource dataSource) {
        DataSourceTerminationTask task = new DataSourceTerminationTask(dataSource, scheduledExecutorService);

        //start now
        scheduledExecutorService.schedule(task, 0, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
