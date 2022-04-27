package com.athena.modules.job.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.modules.job.entity.ScheduleJobLog;
import com.athena.modules.job.repository.ScheduleJobLogRepository;
import com.athena.modules.job.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Mr.sun
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

	@Autowired
	private ScheduleJobLogRepository scheduleJobLogRepository;

	@Override
	public PageUtils queryPage(ScheduleJobLog scheduleJobLog, PageDto pageDto) {

		/*IPage<ScheduleJobLog> page = this.page(
			new Query<ScheduleJobLog>().getPage(pageDto),
			new QueryWrapper<ScheduleJobLog>()
		);*/

		Pageable pageable = PageRequest.of(pageDto.getPage().intValue(), pageDto.getPageSize().intValue());
		Page<ScheduleJobLog> page = scheduleJobLogRepository.findAll(pageable);

		return new PageUtils(page);
	}

}
