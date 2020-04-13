package com.schedule.serviceimpl;

import com.schedule.bean.Time;
import com.schedule.repository.TimeRepository;
import com.schedule.service.TimeService;
import com.schedule.utils.TaskRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import static com.schedule.utils.CommonUtils.*;

/**
 * @author: 李昭
 * @Date: 2020/4/13 10:17
 */
@SuppressWarnings("all")
@Service
public class TimeServiceImpl implements TimeService {

    @Autowired
    TimeRepository timeRepository;


    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;


    @Autowired
    ConcurrentHashMap<Integer, ScheduledFuture<?>> scheduleFutures;


    @Override
    public void startAll() {
        List<Time> allList = timeRepository.getAllList();
        for (Time time : allList) {
            run(time);
        }
    }

    /**
     * 开启任务
     *
     * @param id
     * @return
     */
    @Override
    public boolean startTimeTask(Integer id) {
        if (isEmpty(id)) {
            return false;
        }
        Time task = timeRepository.getTimeTask(id);
        if (task == null) {
            return false;
        }
        if (scheduleFutures.containsKey(id)) {
            ScheduledFuture<?> future = scheduleFutures.get(id);
            if (future != null) {
                future.cancel(false);
                scheduleFutures.remove(id);
            }
        }
        return run(task);
    }


    /**
     * 插入任务的时候就需要开启定时任务
     *
     * @param time
     * @return
     */
    @Override
    public Integer insertTimeTask(Time time) {
        //主键id在后台自动生成
        time.setId(createId());
        int count = 0;
        if (run(time)) {
            time.setExecuteCount(0);
            time.setRunning(true);
            count = timeRepository.insertTimeTask(time);
        } else {
            return 0;
        }
        if (count == 1) {
            return count;
        } else {
            cancel(time.getId());
            return 0;
        }
    }

    /**
     * 取消任务
     *
     * @param id
     * @return
     */
    @Override
    public boolean cancel(Integer id) {
        if (id == null) {
            return false;
        } else {
            ScheduledFuture<?> future = scheduleFutures.get(id);
            if (!isEmpty(future)) {
                //并不会立即终止当前任务
                future.cancel(false);
                scheduleFutures.remove(id);
            }
        }
        return true;
    }

    /**
     * 删除任务
     *
     * @param id
     * @return
     */
    @Override
    public boolean remove(Integer id) {
        if (isEmpty(id)) {
            return false;
        }
        ScheduledFuture<?> future = scheduleFutures.get(id);
        if (!isEmpty(future)) {
            future.cancel(false);
            scheduleFutures.remove(id);
            timeRepository.deleteTimeTask(id);
        }
        return true;
    }

    /**
     * 获取任务
     *
     * @param id
     * @return
     */
    @Override
    public Time getTimeTask(Integer id) {
        if (isEmpty(id)) {
            return null;
        }
        Time time = timeRepository.getTimeTask(id);
        return time;
    }


    private boolean run(Time time) {
        try {
            /**
             * 当前任务为定时任务
             */
            if (!Objects.isNull(time.getExecuteDate())) {
                ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(new TaskRunnable(time), time.getExecuteDate());
                scheduleFutures.put(time.getId(), future);
                return true;
            }
            /**
             * 当前任务为轮询任务
             */
            if (!Objects.isNull(time.getCron())) {
                ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(new TaskRunnable(time), new CronTrigger(time.getCron()));
                scheduleFutures.put(time.getId(), future);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (time.isAutoRemove()) {
                timeRepository.deleteTimeTask(time.getId());
            } else {
                timeRepository.updateExecuteCount(time.getId(), "isRunning", 0);
            }
            return false;
        }
    }
}
