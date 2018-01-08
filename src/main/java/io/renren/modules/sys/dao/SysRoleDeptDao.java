package io.renren.modules.sys.dao;

import io.renren.common.base.CrudDao;
import io.renren.modules.sys.entity.SysRoleDeptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与部门对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:33:46
 */
@Mapper
public interface SysRoleDeptDao extends CrudDao<SysRoleDeptEntity> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<String> queryDeptIdList(String roleId);
}
