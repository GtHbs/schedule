package com.schedule.config;

import com.schedule.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author: 李昭
 * @Date: 2020/4/13 14:35
 */
@Component
public class ApplicationStartRunner implements ApplicationRunner {

    @Autowired
    TimeService timeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        timeService.startAll();
    }
}
