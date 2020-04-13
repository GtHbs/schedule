package com.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: 李昭
 * @Date: 2020/4/13 10:20
 */
@Configuration
public class ScheduleConfiguration {


    /**
     * 创建任务线程容器
     *
     * @return
     */
    @Bean
    public ConcurrentHashMap<Integer, ScheduledFuture<?>> scheduleFutures() {
        return new ConcurrentHashMap<>();
    }


    /**
     * 创建任务线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        /**
         * 配置是否在线程池关闭时,立即取消执行队列中的线程,默认为false即线程池关闭立即抛出一场,其他线程不再执行
         */
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setThreadNamePrefix("TimerTask");
        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        /**
         * 初始化线程池
         */
        scheduler.initialize();
        return scheduler;
    }
}
