package pers.guzx.notice.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 16:37
 * @describe
 */
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Bean("asyncTaskExecutor")
    @Override
    public Executor getAsyncExecutor() {
        return new ThreadPoolExecutor(0, 10,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("email-thread");
                thread.setPriority(2);
                return thread;
            }
        });
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
