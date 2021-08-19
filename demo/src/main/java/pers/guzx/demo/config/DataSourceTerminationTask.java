package pers.guzx.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/19 14:50
 * @describe
 */
@Slf4j
public class DataSourceTerminationTask implements Runnable{
    private static final int MAX_RETRY_TIMES = 10;
    private static final int RETRY_DELAY_IN_MILLISECONDS = 5000;

    private final DataSource dataSourceToTerminate;
    private final ScheduledExecutorService scheduledExecutorService;

    private volatile int retryTimes;

    public DataSourceTerminationTask(DataSource dataSourceToTerminate,
                                     ScheduledExecutorService scheduledExecutorService) {
        this.dataSourceToTerminate = dataSourceToTerminate;
        this.scheduledExecutorService = scheduledExecutorService;
        this.retryTimes = 0;
    }

    @Override
    public void run() {
        if (terminate(dataSourceToTerminate)) {
            log.info("Data source {} terminated successfully!", dataSourceToTerminate);
        } else {
            scheduledExecutorService.schedule(this, RETRY_DELAY_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
        }
    }

    private boolean terminate(DataSource dataSource) {
        log.info("Trying to terminate data source: {}", dataSource);

        try {
            if (dataSource instanceof DruidDataSource) {
                return terminateHikariDataSource((DruidDataSource) dataSource);
            }

            log.error("Not supported data source: {}", dataSource);

            return true;
        } catch (Throwable ex) {
            log.warn("Terminating data source {} failed, will retry in {} ms, error message: {}", dataSource,
                    RETRY_DELAY_IN_MILLISECONDS, ex.getMessage());
            return false;
        } finally {
            retryTimes++;
        }
    }

    /**
     * @see <a href="https://github.com/brettwooldridge/HikariCP/issues/742">Support graceful shutdown of connection
     * pool</a>
     */
    private boolean terminateHikariDataSource(DruidDataSource dataSource) {
//        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        int poolingCount = dataSource.getPoolingCount();
        if (poolingCount > 0) {
            //evict idle connections
//            poolMXBean.softEvictConnections();
            dataSource.removeAbandoned();

            if (dataSource.getActiveCount() > 0 && retryTimes < MAX_RETRY_TIMES) {
                log.warn("Data source {} still has {} active connections, will retry in {} ms.",
                        dataSource,
                        dataSource.getActiveCount(), RETRY_DELAY_IN_MILLISECONDS);
                return false;
            }

            if (dataSource.getActiveCount() > 0) {
                log.warn(
                        "Retry times({}) >= {}, force closing data source {}, with {} active connections!",
                        retryTimes,
                        MAX_RETRY_TIMES, dataSource, dataSource.getActiveCount());
            }
        }

        dataSource.close();

        return true;
    }
}
