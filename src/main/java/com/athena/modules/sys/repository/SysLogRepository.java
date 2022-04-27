package com.athena.modules.sys.repository;


import com.athena.common.base.repository.BaseRepository;
import com.athena.modules.sys.entity.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 系统日志
 *
 * @author Mr.sun
 */
public interface SysLogRepository extends BaseRepository<SysLog, String> {

    
}
