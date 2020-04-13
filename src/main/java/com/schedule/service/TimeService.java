package com.schedule.service;

import com.schedule.bean.Time;

/**
 * @author: 李昭
 * @Date: 2020/4/13 10:16
 */
public interface TimeService {

    /**
     * 插入任务
     *
     * @param time
     * @return
     */
    Integer insertTimeTask(Time time);

    /**
     * 取消任务(不会从数据库中删除)
     *
     * @param id
     * @return
     */
    boolean cancel(Integer id);

    /**
     * 删除任务(会将任务从数据库中删除)
     *
     * @param id
     * @return
     */
    boolean remove(Integer id);

    /**
     * 获取任务
     *
     * @param id
     * @return
     */
    Time getTimeTask(Integer id);

    /**
     * 开启任务
     *
     * @param id
     * @return
     */
    boolean startTimeTask(Integer id);

    /**
     * 服务启动加载所有的任务
     */
    void startAll();
}
