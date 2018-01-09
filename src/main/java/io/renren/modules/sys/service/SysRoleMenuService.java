package io.renren.modules.sys.service;

import io.renren.common.base.CrudService;
import io.renren.modules.sys.dao.SysRoleMenuDao;
import io.renren.modules.sys.entity.SysRoleMenuEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:30
 */
@Service
public class SysRoleMenuService extends CrudService<SysRoleMenuDao, SysRoleMenuEntity> {

    @Transactional
    public void saveOrUpdate(String roleId, List<String> menuIdList) {
        //先删除角色与菜单关系
        dao.delete(roleId);

        if (menuIdList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("menuIdList", menuIdList);
        dao.insert(map);
    }

    /**
     * 根据角色ID，获取菜单ID列表
     */
    public List<String> queryMenuIdList(String roleId) {
        return dao.queryMenuIdList(roleId);
    }

}
