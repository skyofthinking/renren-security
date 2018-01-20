package io.renren.modules.job.helper;

import io.renren.modules.job.entity.ScheduleJobEntity;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;

/**
 * job操作工具类
 *
 * @author jiasx
 * @create 2017/9/21 15:29
 **/
public final class JobDynamicScheduler {
    private static final Logger logger = LoggerFactory.getLogger(JobDynamicScheduler.class);

    /**
     * 获取任务的状态
     *
     * @param scheduler
     * @param scheduleJobEntity
     */
    public static void fillScheduleJobEntity(Scheduler scheduler, ScheduleJobEntity scheduleJobEntity) {
        String group = String.valueOf(scheduleJobEntity.getJobGroup());
        String name = String.valueOf(scheduleJobEntity.getUid());
        TriggerKey triggerKey = TriggerKey.triggerKey(name, group);

        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            TriggerState triggerState = scheduler.getTriggerState(triggerKey);

            if (trigger != null && trigger instanceof CronTriggerImpl) {
                String cronExpression = ((CronTriggerImpl) trigger).getCronExpression();
                scheduleJobEntity.setCronExpression(cronExpression);
            }

            if (triggerState != null) {
                scheduleJobEntity.setStatus(triggerState.name());
            }

        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 根据任务的id和组查询当前任务在quartz中是否存在
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean checkExists(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

    /**
     * 向quartz中添加任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @param cronExpression
     * @param clas
     * @return
     * @throws SchedulerException
     */
    public static boolean addJob(Scheduler scheduler, String jobName, String jobGroup, String cronExpression, String clas) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        JobKey jobKey = new JobKey(jobName, jobGroup);

        if (checkExists(scheduler, jobName, jobGroup)) {
            logger.info("添加任务失败, 任务已存在, jobGroup:{}, jobName:{}", jobGroup, jobName);
            return false;
        }

        // withMisfireHandlingInstructionDoNothing 忽略掉调度终止过程中忽略的调度
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

        Class<? extends Job> jobClass = null;
        try {
            jobClass = (Class<? extends Job>) Class.forName(clas);
        } catch (ClassNotFoundException e) {
            logger.error("添加任务失败，找不到执行的类, jobClass:{}, jobName:{},jobGroup:{}", clas, jobName, jobGroup);
            return false;
        }

        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();
        Date date = scheduler.scheduleJob(jobDetail, cronTrigger);
        logger.info("添加任务成功, jobDetail:{}, cronTrigger:{}, date:{}", jobDetail, cronTrigger, date);
        return true;
    }

    /**
     * 更新任务
     *
     * @param scheduler
     * @param jobGroup
     * @param jobName
     * @param cronExpression
     * @return
     * @throws SchedulerException
     */
    public static boolean rescheduleJob(Scheduler scheduler, String jobGroup, String jobName, String cronExpression) throws SchedulerException {
        if (!checkExists(scheduler, jobName, jobGroup)) {
            logger.info("更新任务失败，任务不存在, JobGroup:{}, JobName:{}", jobGroup, jobName);
            return false;
        }

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        if (oldTrigger != null) {
            String oldCron = oldTrigger.getCronExpression();
            if (oldCron.equals(cronExpression)) {
                return true;
            }

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            oldTrigger = oldTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            scheduler.rescheduleJob(triggerKey, oldTrigger);
        } else {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            JobKey jobKey = new JobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            HashSet<Trigger> triggerSet = new HashSet<Trigger>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
        }

        logger.info("更新任务成功, JobGroup:{}, JobName:{}", jobGroup, jobName);
        return true;
    }

    /**
     * 从quartz中删除任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean removeJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        boolean result = false;
        if (checkExists(scheduler, jobName, jobGroup)) {
            result = scheduler.unscheduleJob(triggerKey);
            logger.info("删除任务成功, triggerKey:{}, result [{}]", triggerKey, result);
        }
        return result;
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean pauseJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

        boolean result = false;
        if (checkExists(scheduler, jobName, jobGroup)) {
            scheduler.pauseTrigger(triggerKey);
            result = true;
            logger.info("暂停任务成功, triggerKey:{}", triggerKey);
        } else {
            logger.info("暂停任务失败, triggerKey:{}", triggerKey);
        }
        return result;
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean resumeJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

        boolean result = false;
        if (checkExists(scheduler, jobName, jobGroup)) {
            scheduler.resumeTrigger(triggerKey);
            result = true;
            logger.info("恢复任务成功, triggerKey:{}", triggerKey);
        } else {
            logger.info("恢复任务失败, triggerKey:{}", triggerKey);
        }
        return result;
    }

    /**
     * 开始运行任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public static boolean triggerJob(Scheduler scheduler, String jobName, String jobGroup, String cronExpression, String clas) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);

        boolean result = false;
        if (checkExists(scheduler, jobName, jobGroup)) {
            scheduler.triggerJob(jobKey);
            result = true;
            logger.info("运行任务成功, jobKey:{}", jobKey);
        } else {
            result = addJob(scheduler, jobName, jobGroup, cronExpression, clas);
            logger.info("运行任务失败, jobKey:{}", jobKey);
        }
        return result;
    }
}