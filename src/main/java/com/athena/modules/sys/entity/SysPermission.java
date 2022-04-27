package com.athena.modules.sys.entity;

import com.athena.common.base.po.BasePo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理
 *
 * @author Mr.sun
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_permission")
public class SysPermission extends BasePo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;


	/**
	 * 父菜单ID，一级菜单为0
	 */
	@Column(length = 32)
	private String parentId;

	/**
	 * 父菜单名称
	 */
	@Transient
	private String parentName;

	/**
	 * 菜单名称
	 */
	@Column(length = 50)
	private String name;

	/**
	 * 菜单URL
	 */
	@Column(length = 200)
	private String url;
	/**
	 * 组件
	 */
	@Column(length = 255)
	private String component;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	@Column(length = 500)
	private String perms;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	@Column(columnDefinition = "tinyint")
	private Integer type;

	/**
	 * 菜单图标
	 */
	@Column(length = 50)
	private String icon;

	/**
	 * 排序
	 */
	private Integer orderNum;

	@Transient
	private List<SysPermission> children;

}
