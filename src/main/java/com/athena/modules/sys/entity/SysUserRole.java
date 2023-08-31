package com.athena.modules.sys.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author Mr.sun
 */
@Data
@Entity
@Table(name = "sys_user_role")
public class SysUserRole implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorConfig")
	@GenericGenerator(name="idGeneratorConfig", strategy = "com.athena.config.IdGeneratorConfig")
	@Column(length = 32)
	private String id;

	/**
	 * 用户ID
	 */
	@Column(length = 32)
	private String userId;

	/**
	 * 角色ID
	 */
	@Column(length = 32)
	private String roleId;


}
