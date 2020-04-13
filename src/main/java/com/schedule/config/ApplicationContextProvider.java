package com.schedule.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 该类用于在运行时从容器中获取对象
 *
 * @author: 李昭
 * @Date: 2020/4/13 10:27
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {


    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(String className, Class<T> tClass) {
        return context.getBean(className, tClass);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(Class<T> tClass) {
        return context.getBean(tClass);
    }
}
