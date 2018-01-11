package io.renren.modules.job.service;

import io.renren.common.base.CrudService;
import io.renren.modules.job.dao.ScheduleJobLogDao;
import io.renren.modules.job.entity.ScheduleJobLogEntity;
import io.renren.modules.sys.dao.SysConfigDao;
import io.renren.modules.sys.entity.SysConfigEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:34:48
 */
@Service
public class ScheduleJobLogService extends CrudService<ScheduleJobLogDao, ScheduleJobLogEntity> {

}
