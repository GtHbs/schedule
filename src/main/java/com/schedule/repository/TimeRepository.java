package com.schedule.repository;

import com.schedule.bean.Time;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 李昭
 * @Date: 2020/4/13 10:07
 */
@Mapper
@Component
public interface TimeRepository {

    /**
     * 先数据库中插入定时任务
     *
     * @param time
     * @return
     */
    Integer insertTimeTask(Time time);

    /**
     * 删除任务
     *
     * @param id
     * @return
     */
    void deleteTimeTask(Integer id);

    /**
     * 更新执行次数
     *
     * @param id
     * @param count
     * @param args
     */
    void updateExecuteCount(@Param("id") Integer id, @Param("count") Object count, @Param("args") Object args);


    /**
     * 获取任务
     *
     * @param id
     * @return
     */
    Time getTimeTask(Integer id);

    /**
     * 获取所有任务
     *
     * @return
     */
    List<Time> getAllList();
}
