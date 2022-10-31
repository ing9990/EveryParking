package com.everyparking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Taewoo
 */

@EnableAsync
@Configuration
public class AsyncConfig {
    private static int CORE_POOL_SIZE = 15;
    private static int MAX_POOL_SIZE = 25;
    private static int QUEUE_CAPACITY = Integer.MAX_VALUE;
    private static String THREAD_NAME_PREFIX = "mail-send-task";

    @Bean
    public Executor mailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.initialize();
        return executor;
    }
}