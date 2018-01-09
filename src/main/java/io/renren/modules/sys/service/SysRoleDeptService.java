package io.renren.modules.sys.service;

import io.renren.common.base.CrudService;
import io.renren.modules.sys.dao.SysRoleDeptDao;
import io.renren.modules.sys.entity.SysRoleDeptEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色与部门对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:42:30
 */
@Service
public class SysRoleDeptService extends CrudService<SysRoleDeptDao, SysRoleDeptEntity> {

    @Transactional
    public void saveOrUpdate(String roleId, List<String> deptIdList) {
        //先删除角色与菜单关系
        dao.delete(roleId);

        if (deptIdList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("deptIdList", deptIdList);
        dao.insert(map);
    }

    /**
     * 根据角色ID，获取部门ID列表
     */
    public List<String> queryDeptIdList(String roleId) {
        return dao.queryDeptIdList(roleId);
    }

}
