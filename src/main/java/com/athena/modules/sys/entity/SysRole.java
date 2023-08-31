package com.athena.modules.sys.entity;

import com.athena.common.base.po.BasePo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 *
 * @author Mr.sun
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sys_role")
public class SysRole extends BasePo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;


	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	@Column(length = 100)
	private String roleName;

	/**
	 * 角色编码
	 */
	@Column(length = 100)
	private String roleCode;

	/**
	 * 备注
	 */
	@Column(length = 200)
	private String remark;

	@Transient
	private List<String> menuIdList;

}
