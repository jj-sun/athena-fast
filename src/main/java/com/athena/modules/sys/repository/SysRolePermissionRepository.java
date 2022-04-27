package com.athena.modules.sys.repository;

import com.athena.common.base.repository.BaseRepository;
import com.athena.modules.sys.entity.SysRolePermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author Mr.sun
 */
public interface SysRolePermissionRepository extends BaseRepository<SysRolePermission, String> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	@Query("SELECT permissionId FROM SysRolePermission WHERE roleId = :roleId")
	List<String> queryPermissionIdList(@Param("roleId") String roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteByRoleIdIn(List<String> roleIds);

	int deleteByPermissionId(String permissionIds);
}
