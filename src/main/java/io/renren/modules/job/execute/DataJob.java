package io.renren.modules.job.execute;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 启动的工作
 *
 * @author jiasx
 * @create 2017/9/21 10:02
 **/
public class DataJob implements Job, Serializable {
    private static final long serialVersionUID = 1L;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Calendar curCalendar = Calendar.getInstance();
            String dayStr = curCalendar.get(Calendar.YEAR) + "-" + (curCalendar.get(Calendar.MONTH) + 1) + "-" + curCalendar.get(Calendar.DAY_OF_MONTH)
                    + " " + curCalendar.get(Calendar.HOUR_OF_DAY)
                    + "::" + curCalendar.get(Calendar.MINUTE)
                    + ":" + curCalendar.get(Calendar.SECOND);
            logger.info("当前执行的任务：{}，时间:{}", context.getJobDetail().getKey(), dayStr);
        } catch (Exception e) {
            logger.info("捕获异常===" + e);
        }
    }
}