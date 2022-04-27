package com.athena.modules.sys.service.impl;

import com.athena.modules.sys.entity.SysRolePermission;
import com.athena.modules.sys.repository.SysRolePermissionRepository;
import com.athena.modules.sys.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



/**
 * 角色与菜单对应关系
 *
 * @author Mr.sun
 */
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

	@Autowired
	private SysRolePermissionRepository rolePermissionRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(String roleId, List<String> permissionIds) {
		//先删除角色与菜单关系
		deleteBatch(List.of(roleId));

		if(permissionIds.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		for(String permissionId : permissionIds){
			SysRolePermission sysRolePermission = new SysRolePermission();
			sysRolePermission.setPermissionId(permissionId);
			sysRolePermission.setRoleId(roleId);

			rolePermissionRepository.save(sysRolePermission);
		}
	}

	@Override
	public List<String> queryPermissionIdList(String roleId) {
		return rolePermissionRepository.queryPermissionIdList(roleId);
	}

	@Override
	public int deleteBatch(List<String> roleIds){
		return rolePermissionRepository.deleteByRoleIdIn(roleIds);
	}

}
