package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysUser;
import com.athena.modules.sys.repository.SysUserRepository;
import com.athena.modules.sys.repository.SysUserRoleRepository;
import com.athena.modules.sys.service.SysUserRoleService;
import com.athena.modules.sys.service.SysUserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.List;


/**
 * 系统用户
 *
 * @author Mr.sun
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserRepository sysUserRepository;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysUserRoleRepository userRoleRepository;

	@Override
	public PageUtils queryPage(SysUser user, PageDto pageDto) {

		Pageable pageable = Query.getPage(pageDto);

		Page<SysUser> page = sysUserRepository.findAll((root, query,builder) -> {
			List<Predicate> predicateList = Lists.newArrayList();
			if(StringUtils.isNotBlank(user.getUsername())) {
				predicateList.add(builder.like(root.get("username"), "%" + user.getUsername() + "%"));
			}
			if(StringUtils.isNotBlank(user.getRealname())) {
				predicateList.add(builder.like(root.get("realname"), "%" + user.getRealname() + "%"));
			}
			return builder.and(predicateList.toArray(new Predicate[0]));
		}, pageable);
		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(String username) {
		return sysUserRepository.queryAllPerms(username);
	}

	@Override
	public SysUser queryByUserName(String username) {
		return sysUserRepository.findSysUserByUsername(username);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUser(SysUser user) {
		//sha256加密
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(StringUtils.isBlank(user.getPassword())) {
			user.setPassword(passwordEncoder.encode("123456"));
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		SysUser saveUser = sysUserRepository.save(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(saveUser.getId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUser user) {
		sysUserRepository.save(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deleteEntity(String id) {
		sysUserRepository.deleteById(id);
		sysUserRoleService.saveOrUpdate(id, null);
		return false;
	}

	@Override
	public void deleteBatch(List<String> ids) {
		sysUserRepository.deleteAllById(ids);
		userRoleRepository.deleteByUserIdIn(ids);
	}

	@Override
	public boolean updatePassword(String username, String newPassword) {
		return sysUserRepository.updatePassword(username, newPassword);
	}


}