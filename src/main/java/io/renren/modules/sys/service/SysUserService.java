package io.renren.modules.sys.service;

import io.renren.common.annotation.DataFilter;
import io.renren.common.base.CrudService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
@Service
public class SysUserService extends CrudService<SysUserDao, SysUserEntity> {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    public List<String> queryAllPerms(String userId) {
        return dao.queryAllPerms(userId);
    }

    /**
     * 查询用户的所有菜单ID
     */
    public List<String> queryAllMenuId(String userId) {
        return dao.queryAllMenuId(userId);
    }

    /**
     * 根据用户名，查询系统用户
     */
    public SysUserEntity queryByUserName(String username) {
        return dao.queryByUserName(username);
    }

    public SysUserEntity queryObject(String userId) {
        return dao.queryObject(userId);
    }

    @DataFilter(tableAlias = "u", user = false)
    public List<SysUserEntity> queryList(Map<String, Object> map) {
        return dao.queryList(map);
    }

    @DataFilter(tableAlias = "u", user = false)
    public int queryTotal(Map<String, Object> map) {
        return dao.queryTotal(map);
    }

    @Override
    @Transactional
    public void insert(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        dao.insert(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        }
        dao.update(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }


    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    public int updatePassword(String userId, String password, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);
        return dao.updatePassword(map);
    }
}
