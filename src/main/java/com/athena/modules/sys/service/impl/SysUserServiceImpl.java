package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.QSysUser;
import com.athena.modules.sys.entity.SysUser;
import com.athena.modules.sys.repository.SysUserRepository;
import com.athena.modules.sys.repository.SysUserRoleRepository;
import com.athena.modules.sys.service.SysUserRoleService;
import com.athena.modules.sys.service.SysUserService;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

	@Autowired
	private JPAQueryFactory queryFactory;

	@Override
	public PageUtils queryPage(SysUser user, PageDto pageDto) {

		Pageable pageable = Query.getPage(pageDto);

		QSysUser quser = QSysUser.sysUser;
		BooleanBuilder builder  =new BooleanBuilder();

		if(StringUtils.isNotBlank(user.getUsername())) {
			builder.and(quser.username.like("%" + user.getUsername() + "%"));
		}
		if(StringUtils.isNotBlank(user.getRealname())) {
			builder.and(quser.realname.like("%" + user.getRealname() + "%"));
		}
		QueryResults<SysUser> page = queryFactory.select(quser).from(quser)
				.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
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