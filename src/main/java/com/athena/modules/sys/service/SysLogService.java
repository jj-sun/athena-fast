package com.athena.modules.sys.service;


import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.modules.sys.entity.SysLog;


/**
 * 系统日志
 *
 * @author Mr.sun
 */
public interface SysLogService {

    PageUtils queryPage(SysLog sysLog, PageDto pageDto);

}
