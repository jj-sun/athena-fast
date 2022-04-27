package com.athena.modules.sys.repository;

import com.athena.common.base.repository.BaseRepository;
import com.athena.modules.sys.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 系统用户
 *
 * @author Mr.sun
 */
public interface SysUserRepository extends BaseRepository<SysUser, String> {

    /**
     * 查询用户的所有权限
     *
     * @param username 用户ID
     */
    @Query("""
           SELECT DISTINCT m.perms FROM SysUserRole ur
           LEFT JOIN SysRolePermission rm ON ur.roleId = rm.roleId
           LEFT JOIN SysPermission m ON rm.permissionId = m.id
           LEFT JOIN SysUser u ON u.id = ur.userId
           WHERE m.perms IS NOT NULL AND u.username = :username
            """)
    List<String> queryAllPerms(@Param("username") String username);

    SysUser findSysUserByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE SysUser SET password = :newPassword WHERE username = :username")
    boolean updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);

}
