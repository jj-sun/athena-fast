package com.athena.modules.sys.service.impl;

import com.athena.modules.sys.entity.SysUserRole;
import com.athena.modules.sys.repository.SysUserRoleRepository;
import com.athena.modules.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



/**
 * 用户与角色对应关系
 *
 * @author Mr.sun
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

	@Autowired
	private SysUserRoleRepository userRoleRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveOrUpdate(String userId, List<String> roleIdList) {
		//先删除用户与角色关系
		userRoleRepository.deleteByUserId(userId);

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}

		//保存用户与角色关系
		for(String roleId : roleIdList){
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(userId);
			sysUserRole.setRoleId(roleId);
			userRoleRepository.save(sysUserRole);
		}
	}

	@Override
	public List<String> queryRoleIdList(String userId) {
		return userRoleRepository.findRoleIdByUserId(userId);
	}

	@Override
	public int deleteBatch(List<String> roleIds){
		return userRoleRepository.deleteByRoleIdIn(roleIds);
	}
}
