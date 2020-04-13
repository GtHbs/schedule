package com.schedule.controller;

import com.schedule.bean.Time;
import com.schedule.service.TimeService;
import com.schedule.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.schedule.utils.CommonUtils.isEmpty;

/**
 * @author: 李昭
 * @Date: 2020/4/13 10:20
 */
@SuppressWarnings("all")
@RequestMapping("/time/")
@RestController
public class TimeController {


    @Autowired
    TimeService timeService;


    /**
     * 插入任务
     * 1, 插入定时任务
     * 2, 插入轮询任务
     *
     * @param time
     * @return
     */
    @PostMapping("insertTimeTask")
    public Result insertTimeTask(Time time) {
        Result result = new Result();
        if (Objects.isNull(time)) {
            result.setCode(500);
            result.setMessage("发送参数为空");
            return result;
        }
        /**
         * 定时任务插入
         *  executeDate,autoRemove,tags不能为空
         */
        if (isEmpty(time.getTags(), time.isAutoRemove()) || (isEmpty(time.getExecuteDate()) && isEmpty(time.getCron()))) {
            result.setMessage("参数错误");
            result.setCode(500);
            return result;
        }
        if (time.isAutoRemove() && isEmpty(time.getExecuteDate())) {
            result.setMessage("参数错误");
            result.setCode(500);
            return result;
        }
        if (!time.isAutoRemove() && isEmpty(time.getCron())) {
            result.setMessage("参数错误");
            result.setCode(500);
            return result;
        }

        Integer task = timeService.insertTimeTask(time);
        if (task > 0) {
            result.setCode(200);
            result.setMessage("插入成功");
            return result;
        }
        result.setMessage("参数错误");
        result.setCode(500);
        return result;
    }


    /**
     * 启动任务
     *
     * @param id
     * @return
     */
    @GetMapping("start")
    public Result start(Integer id) {
        Result<Object> result = new Result<>();
        if (isEmpty(id)) {
            result.setCode(404);
            result.setMessage("参数错误");
            return result;
        }
        if (timeService.startTimeTask(id)) {
            result.setMessage("启动成功");
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMessage("启动失败");
        }
        return result;
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("cancelTask")
    public Result cancel(Integer id) {
        Result<Object> result = new Result<>();
        if (isEmpty(id)) {
            result.setCode(404);
            result.setMessage("参数错误");
            return result;
        }
        if (timeService.cancel(id)) {
            result.setMessage("取消成功");
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMessage("取消失败");
        }
        return result;
    }

    @GetMapping("removeTask")
    public Result<Object> remove(Integer id) {
        Result<Object> result = new Result<>();
        if (isEmpty(id)) {
            result.setCode(404);
            result.setMessage("参数错误");
            return result;
        }
        if (timeService.remove(id)) {
            result.setMessage("删除成功");
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMessage("删除失败");
        }
        return result;
    }

    @GetMapping("getTask")
    public Result getTask(Integer id) {
        Result<Object> result = new Result<>();
        if (isEmpty(id)) {
            result.setCode(404);
            result.setMessage("参数错误");
            return result;
        }
        Time time = timeService.getTimeTask(id);
        if (!Objects.isNull(time)) {
            result.setMessage(time);
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMessage("获取失败");
        }
        return result;
    }
}
