package com.athena.modules.job.service;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.modules.job.entity.ScheduleJobLog;

/**
 * 定时任务日志
 *
 * @author Mr.sun
 */
public interface ScheduleJobLogService {

	PageUtils queryPage(ScheduleJobLog scheduleJobLog, PageDto pageDto);
	
}
