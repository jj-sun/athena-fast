package com.athena.modules.job.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务日志
 *
 * @author Mr.sun
 */
@Data
@Entity
@Table(name = "schedule_job_log")
public class ScheduleJobLog implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorConfig")
	@GenericGenerator(name="idGeneratorConfig", strategy = "com.athena.config.IdGeneratorConfig")
	@Column(length = 32)
	private String id;
	
	/**
	 * 任务id
	 */
	@Column(length = 32)
	private String jobId;
	
	/**
	 * spring bean名称
	 */
	@Column(length = 200)
	private String beanName;
	
	/**
	 * 参数
	 */
	@Column(length = 2000)
	private String parameter;
	
	/**
	 * 任务状态    0：成功    1：失败
	 */
	@Column(length = 4, columnDefinition = "TINYINT")
	private Integer status;
	
	/**
	 * 失败信息
	 */
	@Column(length = 2000)
	private String error;
	
	/**
	 * 耗时(单位：毫秒)
	 */
	private Integer times;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date ctime;
	
}
