package io.renren.modules.job.entity;

import java.util.Date;

import io.renren.common.base.DataEntity;
import org.springframework.data.annotation.Transient;


/**
 * 定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-01-20 14:47:22
 */
public class ScheduleJobEntity extends DataEntity<ScheduleJobEntity> {
    private static final long serialVersionUID = 1L;

    //任务分组名称
    private String jobGroup = "defaultGroup";
    //定时任务类
    private String jobClass;
    //Cron表达式
    private String cronExpression;
    //任务描述
    private String description;
    //数据-使用json字符串去存储
    private String data;

    //从quartz中获取
    @Transient
    private String status; // 任务状态 【base on quartz】

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
