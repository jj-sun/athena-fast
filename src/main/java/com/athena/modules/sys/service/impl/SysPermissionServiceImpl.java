package com.athena.modules.sys.service.impl;


import com.athena.common.constant.Constant;
import com.athena.common.utils.SecurityUtil;
import com.athena.common.utils.TreeUtils;
import com.athena.modules.sys.entity.SysPermission;
import com.athena.modules.sys.repository.SysPermissionRepository;
import com.athena.modules.sys.repository.SysRolePermissionRepository;
import com.athena.modules.sys.service.SysPermissionService;
import com.athena.modules.sys.vo.SysMenuTree;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Mr.sun
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private SysPermissionRepository permissionRepository;

	@Autowired
	private SysRolePermissionRepository rolePermissionRepository;

	@Override
	public List<SysPermission> permissionTree() {
		List<SysPermission> sysPermissionEntities = permissionRepository.findAll();
		if(!CollectionUtils.isEmpty(sysPermissionEntities)) {
			List<SysPermission> roots = sysPermissionEntities.stream().filter(root -> root.getParentId().equals(Constant.TREE_ROOT)).sorted(Comparator.comparing(SysPermission::getOrderNum)).collect(Collectors.toList());
			buildTree(roots, sysPermissionEntities);
			return roots;
		}
		return sysPermissionEntities;
	}
	private void buildTree(List<SysPermission> roots, List<SysPermission> all) {
		if(CollectionUtils.isEmpty(roots)) {
			return;
		}
		roots.forEach(root -> {
			List<SysPermission> childrenList = all.stream().filter(tree -> tree.getParentId().equals(root.getId())).sorted(Comparator.comparing(SysPermission::getOrderNum)).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(childrenList)) {
				root.setChildren(childrenList);
				buildTree(childrenList, all);
			}
		});
	}

	@Override
	public List<SysPermission> queryListParentId(String parentId, List<String> menuIdList) {
		List<SysPermission> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysPermission> userMenuList = new ArrayList<>();
		for(SysPermission menu : menuList){
			if(menuIdList.contains(menu.getId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysPermission> queryListParentId(String parentId) {
		return permissionRepository.findByParentIdOrderByOrderNum(parentId);
	}

	@Override
	public List<SysMenuTree> treeSelect() {
		List<SysPermission> sysPermissionEntities = permissionRepository.findAll();
		return this.getTree(sysPermissionEntities);
	}

	@Override
	public List<SysMenuTree> getUserPermissionTree(String username) {
		List<SysPermission> sysPermissionEntities;

		//系统管理员，拥有最高权限
		if(username.equals(Constant.SUPER_ADMIN)){
			sysPermissionEntities = permissionRepository.findByTypeNot(Constant.PermissionType.BUTTON.getValue());
		} else {
			//用户菜单列表
			List<String> menuIdList = permissionRepository.queryAllMenuId(SecurityUtil.getLoginUser().getId());
			sysPermissionEntities = permissionRepository.findByIdInAndTypeNot(menuIdList,Constant.PermissionType.BUTTON.getValue());
		}
		return this.getTree(sysPermissionEntities);
	}

	private List<SysMenuTree> getTree(List<SysPermission> sysPermissionEntities) {
		List<SysMenuTree> menuTreeList = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(sysPermissionEntities)) {
			sysPermissionEntities.forEach(sysPermissionEntity -> menuTreeList.add(new SysMenuTree(sysPermissionEntity)));
			List<SysMenuTree> root = menuTreeList.stream().filter(sysMenuTree -> sysMenuTree.getParentKey().equals(Constant.TREE_ROOT)).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(root)) {
				TreeUtils.buildTree(root, menuTreeList);
				return root;
			}
		}
		return null;
	}

	@Override
	public void delete(String permissionId){
		//删除菜单
		permissionRepository.deleteById(permissionId);
		//删除菜单与角色关联
		rolePermissionRepository.deleteByPermissionId(permissionId);
	}

}
