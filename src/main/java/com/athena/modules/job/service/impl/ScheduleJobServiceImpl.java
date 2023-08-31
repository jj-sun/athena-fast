package com.athena.modules.job.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.constant.Constant;
import com.athena.common.utils.DateUtils;
import com.athena.common.utils.PageUtils;
import com.athena.modules.job.entity.ScheduleJob;
import com.athena.modules.job.repository.ScheduleJobRepository;
import com.athena.modules.job.service.ScheduleJobService;
import com.athena.modules.job.utils.ScheduleUtils;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mr.sun
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
	@Autowired
    private Scheduler scheduler;

	@Autowired
	private ScheduleJobRepository scheduleJobRepository;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJob> scheduleJobList = scheduleJobRepository.findAll();
		for(ScheduleJob scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}

	@Override
	public PageUtils queryPage(ScheduleJob scheduleJob, PageDto pageDto) {

		/*IPage<ScheduleJob> page = this.page(
			new Query<ScheduleJob>().getPage(pageDto),
			new LambdaQueryWrapper<ScheduleJob>().like(StringUtils.isNotBlank(scheduleJob.getBeanName()), ScheduleJob::getBeanName, scheduleJob.getBeanName())
		);*/
		Pageable pageable = PageRequest.of(pageDto.getPage().intValue(), pageDto.getPageSize().intValue());
		Page<ScheduleJob> page = scheduleJobRepository.findAll((root, query, builder) -> {
			List<Predicate> predicateList = Lists.newArrayList();
			if(StringUtils.isNotBlank(scheduleJob.getBeanName())) {
				predicateList.add(builder.like(root.get("beanName"), "%" + scheduleJob.getBeanName() + "%"));
			}
			return builder.and(predicateList.toArray(new Predicate[0]));
		},pageable);

		return new PageUtils(page);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveJob(ScheduleJob scheduleJob) {
		scheduleJob.setCtime(DateUtils.now());
		scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
		scheduleJobRepository.save(scheduleJob);
        
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

		scheduleJobRepository.save(scheduleJob);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
		scheduleJobRepository.deleteAllByIdInBatch(jobIds);
	}

	@Override
    public int updateBatch(List<String> jobIds, int status){
    	return scheduleJobRepository.updateBatch(jobIds, status);
    }
    
	@Override
	@Transactional(rollbackFor = Exception.class)
    public void run(List<String> jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.run(scheduler, scheduleJobRepository.getById(jobId));
    	}
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void pause(List<String> jobIds) {
        for(String jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void resume(List<String> jobIds) {
    	for(String jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }
    
}
