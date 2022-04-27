package com.athena.modules.sys.controller;

import com.athena.common.annotation.Log;
import com.athena.common.base.dto.PageDto;
import com.athena.common.base.select.BaseSelect;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Result;
import com.athena.common.validator.ValidatorUtils;
import com.athena.modules.sys.entity.SysRole;
import com.athena.modules.sys.repository.SysRoleRepository;
import com.athena.modules.sys.service.SysRoleService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 角色管理
 *
 * @author Mr.sun
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysRoleRepository roleRepository;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	//@PreAuthorize("hasAuthority('sys:role:list')")
	public Result<PageUtils> list(SysRole role, PageDto pageDto){

		PageUtils page = sysRoleService.queryPage(role, pageDto);

		return Result.ok(page);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	//@PreAuthorize("hasAuthority('sys:role:select')")
	public Result<List<BaseSelect>> select(){
		List<SysRole> list = roleRepository.findAll();
		List<BaseSelect> selectList = Lists.newArrayList();
		if(!CollectionUtils.isEmpty(list)) {
			list.forEach(role -> selectList.add(new BaseSelect(role.getRoleName(), role.getId())));
		}
		return Result.ok(selectList);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	//@PreAuthorize("hasAuthority('sys:role:info')")
	public Result<SysRole> info(@PathVariable("roleId") String roleId){
		SysRole role = roleRepository.getById(roleId);
		
		return Result.ok(role);
	}
	
	/**
	 * 保存角色
	 */
	@Log("保存角色")
	@PostMapping("/save")
	//@PreAuthorize("hasAuthority('sys:role:save')")
	public Result<Object> save(@RequestBody SysRole role){
		ValidatorUtils.validateEntity(role);

		roleRepository.save(role);
		
		return Result.ok();
	}
	
	/**
	 * 修改角色
	 */
	@Log("修改角色")
	@PutMapping("/update")
	//@PreAuthorize("hasAuthority('sys:role:update')")
	public Result<Object> update(@RequestBody SysRole role){
		ValidatorUtils.validateEntity(role);

		roleRepository.save(role);
		
		return Result.ok();
	}
	
	/**
	 * 删除角色
	 */
	@Log("删除角色")
	@DeleteMapping("/delete")
	//@PreAuthorize("hasAuthority('sys:role:delete')")
	public Result<Object> delete(@RequestParam(name = "id") String id){
		sysRoleService.deleteEntity(id);
		return Result.ok();
	}

	@Log("批量删除角色")
	@DeleteMapping("/deleteBatch")
	public Result<Object> deleteBatch(@RequestParam(name = "ids") String ids) {
		sysRoleService.deleteBatch(Arrays.asList(ids.split(",")));
		return Result.ok();
	}
}
