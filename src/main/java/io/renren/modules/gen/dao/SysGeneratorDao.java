package io.renren.modules.gen.dao;

import io.renren.common.base.CrudDao;
import io.renren.modules.gen.entity.SysGeneratorEntity;
import io.renren.modules.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午3:32:04
 */
@Mapper
public interface SysGeneratorDao extends CrudDao<SysGeneratorEntity> {

    Map<String, String> queryTable(Map<String, Object> map);

    List<Map<String, String>> queryColumns(Map<String, Object> map);
}
