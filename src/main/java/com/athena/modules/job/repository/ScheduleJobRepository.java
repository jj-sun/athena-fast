package com.athena.modules.job.repository;

import com.athena.common.base.repository.BaseRepository;
import com.athena.modules.job.entity.ScheduleJob;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author Mr.sun
 */
public interface ScheduleJobRepository extends BaseRepository<ScheduleJob, String> {
	
	/**
	 * 批量更新状态
	 */
	@Modifying
	@Query("update ScheduleJob set status = :status where id in :jobIds")
	int updateBatch(@Param("jobIds") List<String> jobIds,@Param("status") int status);
}
