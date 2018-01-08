package io.renren.modules.sys.entity;


import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:28:39
 */
public class SysUserRoleEntity extends DataEntity<SysUserRoleEntity> {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 设置：用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户ID
     *
     * @return Long
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置：角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取：角色ID
     *
     * @return Long
     */
    public String getRoleId() {
        return roleId;
    }

}
