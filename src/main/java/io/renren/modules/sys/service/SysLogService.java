package io.renren.modules.sys.service;

import io.renren.common.base.CrudService;
import io.renren.modules.sys.dao.SysLogDao;
import io.renren.modules.sys.entity.SysLogEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Service
public class SysLogService extends CrudService<SysLogDao, SysLogEntity> {

}
