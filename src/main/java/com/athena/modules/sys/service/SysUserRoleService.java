

package com.athena.modules.sys.service;

import java.util.List;



/**
 * 用户与角色对应关系
 *
 * @author Mr.sun
 */
public interface SysUserRoleService {
	
	void saveOrUpdate(String userId, List<String> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<String> queryRoleIdList(String userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(List<String> roleIds);
}
