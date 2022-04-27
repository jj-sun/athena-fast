package com.athena.modules.job.entity;

import com.athena.common.base.po.BasePo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * 定时任务
 *
 * @author Mr.sun
 */
@Data
@Entity
@Table(name = "schedule_job")
public class ScheduleJob extends BasePo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务调度参数key
	 */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

	/**
	 * spring bean名称
	 */
	@NotBlank(message="bean名称不能为空")
	@Column(length = 200)
	private String beanName;
	
	/**
	 * 参数
	 */
	@Column(length = 2000)
	private String parameter;
	
	/**
	 * cron表达式
	 */
	@NotBlank(message="cron表达式不能为空")
	@Column(length = 100)
	private String cronExpression;

	/**
	 * 任务状态
	 */
	@Column(columnDefinition = "TINYINT")
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;



}
