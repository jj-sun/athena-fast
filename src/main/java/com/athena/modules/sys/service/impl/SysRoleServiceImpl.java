package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysRole;
import com.athena.modules.sys.repository.SysRoleRepository;
import com.athena.modules.sys.service.SysRolePermissionService;
import com.athena.modules.sys.service.SysRoleService;
import com.athena.modules.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色
 *
 * @author Mr.sun
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleRepository roleRepository;

	@Override
	public PageUtils queryPage(SysRole role, PageDto pageDto) {

		/*IPage<SysRole> page = this.page(
			new Query<SysRole>().getPage(pageDto)
		);*/
		Pageable pageable = Query.getPage(pageDto);
		Page<SysRole> page = roleRepository.findAll(pageable);

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteEntity(String id) {
		//删除角色与菜单关联
		sysRolePermissionService.deleteBatch(List.of(id));

		//删除角色与用户关联
		sysUserRoleService.deleteBatch(List.of(id));
		roleRepository.deleteById(id);
		return true;
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> ids) {
        //删除角色
        roleRepository.deleteAllByIdInBatch(ids);

        //删除角色与菜单关联
        sysRolePermissionService.deleteBatch(ids);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(ids);
    }


    @Override
	public List<String> queryRoleIdList(String username) {
		return roleRepository.findRoleIdListByUsername(username);
	}

}
