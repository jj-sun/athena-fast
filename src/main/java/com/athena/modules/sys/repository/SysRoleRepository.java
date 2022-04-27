package com.athena.modules.sys.repository;

import com.athena.common.base.repository.BaseRepository;
import com.athena.modules.sys.entity.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 角色管理
 *
 * @author Mr.sun
 */
public interface SysRoleRepository extends BaseRepository<SysRole, String> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	@Query("SELECT id FROM SysRole WHERE creator = :username")
	List<String> findRoleIdListByUsername(@Param("username") String username);
}
