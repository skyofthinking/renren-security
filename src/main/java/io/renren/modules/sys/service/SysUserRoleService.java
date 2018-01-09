package io.renren.modules.sys.service;

import io.renren.common.base.CrudService;
import io.renren.modules.sys.dao.SysUserRoleDao;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:24
 */
@Service
public class SysUserRoleService extends CrudService<SysUserRoleDao, SysUserRoleEntity> {

    public void saveOrUpdate(String userId, List<String> roleIdList) {
        if (roleIdList.size() == 0) {
            return;
        }

        //先删除用户与角色关系
        dao.delete(userId);

        //保存用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIdList", roleIdList);
        dao.insert(map);
    }

    /**
     * 根据用户ID，获取角色ID列表
     */
    public List<String> queryRoleIdList(String userId) {
        return dao.queryRoleIdList(userId);
    }

}
