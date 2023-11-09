package org.xzx.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {
    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor asyncServiceExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);

        executor.setMaxPoolSize(20);

        executor.setQueueCapacity(Integer.MAX_VALUE);

        executor.setKeepAliveSeconds(60);

        executor.setThreadNamePrefix("taskExecutor");

        executor.setWaitForTasksToCompleteOnShutdown(true);

        executor.initialize();

        return executor;
    }
}