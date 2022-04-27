package com.athena.modules.sys.service;

import java.util.List;



/**
 * 角色与菜单对应关系
 *
 * @author Mr.sun
 */
public interface SysRolePermissionService {
	
	void saveOrUpdate(String roleId, List<String> permissionIds);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<String> queryPermissionIdList(String roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(List<String> roleIds);
	
}
