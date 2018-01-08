package io.renren.modules.oss.dao;

import io.renren.common.base.CrudDao;
import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 12:13:26
 */
@Mapper
public interface SysOssDao extends CrudDao<SysOssEntity> {

}
