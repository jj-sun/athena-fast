package com.athena.modules.sys.repository;

import com.athena.common.base.repository.BaseRepository;
import com.athena.modules.sys.entity.SysPermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 菜单管理
 *
 * @author Mr.sun
 */
public interface SysPermissionRepository extends BaseRepository<SysPermission, String> {

	/**
	 * 查询用户的所有菜单ID
	 */
	@Query("SELECT DISTINCT rm.permissionId FROM SysUserRole ur LEFT JOIN SysRolePermission rm ON ur.roleId = rm.roleId  WHERE ur.userId = :userId")
	List<String> queryAllMenuId(@Param("userId") String userId);

	List<SysPermission> findByParentIdOrderByOrderNum(String parentId);

	List<SysPermission> findByTypeNot(int type);

	List<SysPermission> findByIdInAndTypeNot(@Nullable List<String> id, int type);

}
