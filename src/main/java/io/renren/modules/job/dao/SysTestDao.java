package io.renren.modules.job.dao;

import io.renren.modules.job.entity.SysTestEntity;
import org.apache.ibatis.annotations.Mapper;
import io.renren.common.base.CrudDao;

/**
 * 测试表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-02-15 16:32:03
 */
@Mapper
public interface SysTestDao extends CrudDao<SysTestEntity> {

}
