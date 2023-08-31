package com.athena.modules.sys.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * 角色与菜单对应关系
 *
 * @author Mr.sun
 */
@Data
@Entity
@Table(name = "sys_role_permission")
public class SysRolePermission implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorConfig")
	@GenericGenerator(name="idGeneratorConfig", strategy = "com.athena.config.IdGeneratorConfig")
	@Column(length = 32)
	private String id;

	/**
	 * 角色ID
	 */
	@Column(length = 32)
	private String roleId;

	/**
	 * 菜单ID
	 */
	@Column(length = 32)
	private String permissionId;
	
}
