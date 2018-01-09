package io.renren.modules.sys.service;

import io.renren.common.annotation.DataFilter;
import io.renren.common.base.CrudService;
import io.renren.modules.sys.dao.SysRoleDao;
import io.renren.modules.sys.entity.SysRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
@Service
public class SysRoleService extends CrudService<SysRoleDao, SysRoleEntity> {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    @DataFilter(tableAlias = "r", user = false)
    public List<SysRoleEntity> queryList(Map<String, Object> map) {
        return dao.queryList(map);
    }

    @Override
    @DataFilter(tableAlias = "r", user = false)
    public int queryTotal(Map<String, Object> map) {
        return dao.queryTotal(map);
    }

    @Override
    @Transactional
    public void insert(SysRoleEntity role) {
        role.setCreateTime(new Date());
        dao.insert(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional
    public int update(SysRoleEntity role) {
        int count = dao.update(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());

        return count;
    }
}
