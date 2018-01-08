package io.renren.modules.sys.entity;


import java.io.Serializable;

/**
 * 角色与部门对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:28:13
 */
public class SysRoleDeptEntity extends DataEntity<SysRoleDeptEntity> {
	/**
	 * 角色ID
	 */
	private String roleId;

	/**
	 * 部门ID
	 */
	private String deptId;

	/**
	 * 设置：角色ID
	 * @param roleId 角色ID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取：角色ID
	 * @return Long
	 */
	public String getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置：部门ID
	 * @param deptId 部门ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * 获取：部门ID
	 * @return Long
	 */
	public String getDeptId() {
		return deptId;
	}
	
}
