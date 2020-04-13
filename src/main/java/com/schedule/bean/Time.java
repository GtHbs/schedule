package com.schedule.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author: 李昭
 * @Date: 2020/4/13 10:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Time {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用于区分不同的消息
     */
    private String tags;
    /**
     * 任务内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 定时任务执行时间
     */
    private Date executeDate;
    /**
     * 轮询任务表达式
     */
    private String cron;
    /**
     * 轮询任务执行次数
     */
    private Integer executeCount;
    /**
     * 任务执行状态
     */
    private boolean isRunning;
    /**
     * 是否为自动删除(定时任务)
     */
    private boolean autoRemove;
}
