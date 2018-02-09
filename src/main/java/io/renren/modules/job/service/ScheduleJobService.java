package io.renren.modules.job.service;

import com.github.pagehelper.PageHelper;
import io.renren.common.utils.Constant;
import io.renren.common.utils.Query;
import io.renren.modules.job.entity.ScheduleJobEntity;
import io.renren.modules.job.dao.ScheduleJobDao;
import io.renren.common.base.CrudService;
import io.renren.modules.job.helper.JobDynamicScheduler;
import io.renren.modules.sys.entity.SysConfigEntity;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-01-20 14:47:22
 */
@Service
public class ScheduleJobService extends CrudService<ScheduleJobDao, ScheduleJobEntity> {

    @Autowired
    @Qualifier("schedulerFactoryBean")
    private SchedulerFactoryBean schedulerFactoryBean;

    public List<ScheduleJobEntity> queryList(Query query) {
        List<ScheduleJobEntity> scheduleJobEntityList = dao.queryList(query);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for (ScheduleJobEntity mScheduleJobEntity : scheduleJobEntityList) {
            JobDynamicScheduler.fillScheduleJobEntity(scheduler, mScheduleJobEntity);
        }
        return scheduleJobEntityList;
    }

    @Override
    public void insert(ScheduleJobEntity scheduleJobEntity) {
        super.save(scheduleJobEntity);
        String jobName = scheduleJobEntity.getUid();
        String jobGroup = scheduleJobEntity.getJobGroup();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            JobDynamicScheduler.addJob(scheduler, jobName, jobGroup, scheduleJobEntity.getCronExpression(), scheduleJobEntity.getJobClass());
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            try {
                super.delete(scheduleJobEntity.getUid());
                JobDynamicScheduler.removeJob(scheduler, jobName, jobGroup);
            } catch (SchedulerException se) {
                logger.error(e.getMessage(), se);
            }
        }
    }

    @Override
    public void update(ScheduleJobEntity scheduleJobEntity) {
        super.update(scheduleJobEntity);
        String jobName = scheduleJobEntity.getUid();
        String jobGroup = scheduleJobEntity.getJobGroup();
        try {
            boolean ret = JobDynamicScheduler.rescheduleJob(schedulerFactoryBean.getScheduler(), jobGroup, jobName, scheduleJobEntity.getCronExpression());
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteBatch(String[] uids) {
        for (String uid : uids) {
            ScheduleJobEntity scheduleJobEntity = super.queryObject(uid);
            String jobName = scheduleJobEntity.getUid();
            String jobGroup = scheduleJobEntity.getJobGroup();
            try {
                JobDynamicScheduler.removeJob(schedulerFactoryBean.getScheduler(), jobName, jobGroup);
                super.delete(uid);
            } catch (SchedulerException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void delete(String uid) {
        ScheduleJobEntity scheduleJobEntity = super.queryObject(uid);
        String jobName = scheduleJobEntity.getUid();
        String jobGroup = scheduleJobEntity.getJobGroup();
        try {
            JobDynamicScheduler.removeJob(schedulerFactoryBean.getScheduler(), jobName, jobGroup);
            super.delete(uid);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 暂停任务
     **/
    public boolean pause(String[] uids) {
        for (String uid : uids) {
            ScheduleJobEntity scheduleJobEntity = super.queryObject(uid);
            String jobName = scheduleJobEntity.getUid();
            String jobGroup = scheduleJobEntity.getJobGroup();
            try {
                JobDynamicScheduler.pauseJob(schedulerFactoryBean.getScheduler(), jobName, jobGroup);    // JobStatus Do Not Store
            } catch (SchedulerException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        return true;
    }

    /**
     * 恢复任务
     **/
    public boolean resume(String[] uids) {
        for (String uid : uids) {
            ScheduleJobEntity scheduleJobEntity = super.queryObject(uid);
            String jobName = scheduleJobEntity.getUid();
            String jobGroup = scheduleJobEntity.getJobGroup();
            try {
                JobDynamicScheduler.resumeJob(schedulerFactoryBean.getScheduler(), jobName, jobGroup);
            } catch (SchedulerException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        return true;
    }

    /**
     * 执行任务
     **/
    public boolean run(String[] uids) {
        for (String uid : uids) {
            ScheduleJobEntity scheduleJobEntity = super.queryObject(uid);
            String jobName = scheduleJobEntity.getUid();
            String jobGroup = scheduleJobEntity.getJobGroup();
            try {
                JobDynamicScheduler.triggerJob(schedulerFactoryBean.getScheduler(), jobName, jobGroup, scheduleJobEntity.getCronExpression(), scheduleJobEntity.getJobClass());
            } catch (SchedulerException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }
        return true;
    }
}
