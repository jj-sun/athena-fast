package com.athena.modules.sys.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志
 *
 * @author Mr.sun
 */
@Data
@Entity
@Table(name = "sys_log")
public class SysLog implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorConfig")
	@GenericGenerator(name="idGeneratorConfig", strategy = "com.athena.config.IdGeneratorConfig")
	@Column(length = 32)
	private String id;
	//日志类型0登录日志1操作日志
	@Column(columnDefinition = "tinyint")
	private Integer logType;
	//用户名
	@Column(length = 50)
	private String username;
	//用户操作
	@Column(length = 50)
	private String operation;
	//请求方法
	@Column(length = 200)
	private String method;
	//请求参数
	@Column(length = 5000)
	private String params;
	//执行时长(毫秒)
	private Long time;
	//IP地址
	@Column(length = 64)
	private String ip;
	//创建时间
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createDate;

}
