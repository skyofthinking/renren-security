package io.renren.modules.job.dao;

import io.renren.modules.job.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;
import io.renren.common.base.CrudDao;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-01-20 14:47:22
 */
@Mapper
public interface ScheduleJobDao extends CrudDao<ScheduleJobEntity> {

}
