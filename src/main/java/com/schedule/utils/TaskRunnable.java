package com.schedule.utils;

import com.schedule.bean.Time;
import com.schedule.config.ApplicationContextProvider;
import com.schedule.repository.TimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * @author: 李昭
 * @Date: 2020/4/13 10:19
 */
@Slf4j
public class TaskRunnable implements Runnable {

    private Time time;

    public TaskRunnable(Time time) {
        this.time = time;
    }

    @Override
    public void run() {
        log.info("定时任务启动:",time);
        RocketMQTemplate template = ApplicationContextProvider.getBean(RocketMQTemplate.class);
        TimeRepository repository = ApplicationContextProvider.getBean(TimeRepository.class);
        /**
         * 向time主题中发送消息
         */
        template.syncSend("time",time.getContent());
        /**
         * 定时任务
         * 如果当前任务为自动删除,则直接将其从数据库中删除,否则更新访问次数
         */
        if (time.isAutoRemove()) {
            log.info("一次性任务删除");
            repository.deleteTimeTask(time.getId());
        } else {
            /**
             * 轮询任务
             * 更新执行次数
             */
            log.info("轮询任务更新执行次数");
            repository.updateExecuteCount(time.getId(),"executeCount",time.getExecuteCount() + 1);
        }
    }
}
