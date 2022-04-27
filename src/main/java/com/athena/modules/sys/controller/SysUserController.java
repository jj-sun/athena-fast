package com.athena.modules.sys.controller;

import com.athena.common.annotation.Log;
import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Result;
import com.athena.common.validator.Assert;
import com.athena.common.validator.ValidatorUtils;
import com.athena.common.validator.group.AddGroup;
import com.athena.common.validator.group.UpdateGroup;
import com.athena.modules.sys.entity.SysUser;
import com.athena.modules.sys.form.PasswordForm;
import com.athena.modules.sys.repository.SysUserRepository;
import com.athena.modules.sys.service.SysUserRoleService;
import com.athena.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统用户
 *
 * @author Mr.sun
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

	@Autowired
	private SysUserRepository sysUserRepository;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:user:list')")
	public Result<PageUtils> list(SysUser user, PageDto pageDto){
		PageUtils page = sysUserService.queryPage(user, pageDto);
		return Result.ok(page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public Result<SysUser> info(){
		return Result.ok(getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@Log("修改密码")
	@PostMapping("/password")
	public Result<Object> password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//sha256加密
		String newPassword = passwordEncoder.encode(form.getNewPassword());

		//更新密码
		boolean flag = sysUserService.updatePassword(getUsername(), newPassword);
		if(!flag){
			return Result.error("原密码不正确");
		}
		
		return Result.ok();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{id}")
	//@PreAuthorize("hasAuthority('sys:user:info')")
	public Result<SysUser> info(@PathVariable("id") String id){
		SysUser user = sysUserRepository.getById(id);
		
		//获取用户所属的角色列表
		List<String> roleIdList = sysUserRoleService.queryRoleIdList(id);
		user.setRoleIdList(roleIdList);
		
		return Result.ok(user);
	}
	
	/**
	 * 保存用户
	 */
	@Log("保存用户")
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:user:save')")
	public Result<Object> save(@RequestBody SysUser user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		sysUserService.saveUser(user);
		
		return Result.ok();
	}
	
	/**
	 * 修改用户
	 */
	@Log("修改用户")
	@PutMapping("/update")
	@PreAuthorize("hasAuthority('sys:user:update')")
	public Result<Object> update(@RequestBody SysUser user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);
		
		return Result.ok();
	}
	
	/**
	 * 删除用户
	 */
	@Log("删除用户")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:user:delete')")
	public Result<Object> delete(@RequestParam(name = "id") String id){
		sysUserService.deleteEntity(id);
		return Result.ok();
	}

	@Log("批量删除用户")
	@DeleteMapping("/deleteBatch")
	@PreAuthorize("hasAuthority('sys:user:deleteBatch')")
	public Result<Object> deleteBatch(@RequestParam(name = "ids") String ids) {
		sysUserService.deleteBatch(Arrays.asList(ids.split(",")));
		return Result.ok();
	}
}
