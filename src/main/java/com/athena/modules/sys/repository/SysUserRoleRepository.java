package com.athena.modules.sys.repository;

import com.athena.common.base.repository.BaseRepository;
import com.athena.modules.sys.entity.SysUserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @author Mr.sun
 */
public interface SysUserRoleRepository extends BaseRepository<SysUserRole, String> {

    /**
     * 根据用户ID，获取角色ID列表
     */

    @Query("SELECT roleId FROM SysUserRole WHERE userId = :userId")
    List<String> findRoleIdByUserId(@Param("userId") String userId);


    /**
     * 根据角色ID数组，批量删除
     */
    //int deleteBatch(List<String> roleIds);

    int deleteByRoleIdIn(List<String> roleIds);

    void deleteByUserId(String userId);

    void deleteByUserIdIn(List<String> userIds);
}
