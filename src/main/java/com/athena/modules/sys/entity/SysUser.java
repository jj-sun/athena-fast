package com.athena.modules.sys.entity;

import com.athena.common.annotation.Dict;
import com.athena.common.base.po.BasePo;
import com.athena.common.validator.group.AddGroup;
import com.athena.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统用户
 *
 * @author Mr.sun
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_user")
public class SysUser extends BasePo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */

	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Column(unique = true, nullable = false, length = 50)
	private String username;
	@Column(name = "realname", nullable = false)
	private String realname;

	/**
	 * 密码
	 */
	//@NotBlank(message="密码不能为空", groups = AddGroup.class)
	@Column(length = 100)
	private String password;

	/**
	 * 盐
	 */
	@Column(length = 20)
	private String salt;

	/**
	 * 邮箱
	 */
	@NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	@Column(length = 100)
	private String email;

	/**
	 * 手机号
	 */
	@Column(length = 100)
	private String mobile;

	/**
	 * 性别
	 */
	@Column(columnDefinition = "tinyint")
	private Integer sex;

	/**
	 * 部门code
	 */
	@Column(length = 32)
	@Dict(dictTable = "sys_dept", dicCode = "id", dicText = "dept_name")
	private String deptCode;

	@Column(columnDefinition = "tinyint")
	private Integer userType;

	/**
	 * 角色ID列表
	 */
	@Transient
	private List<String> roleIdList;

}
