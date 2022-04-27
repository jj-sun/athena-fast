package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysLog;
import com.athena.modules.sys.repository.SysLogRepository;
import com.athena.modules.sys.service.SysLogService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;


/**
 * @author Mr.sun
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepository logRepository;

    @Override
    public PageUtils queryPage(SysLog sysLog, PageDto pageDto) {

       /* IPage<SysLog> page = this.page(
            new Query<SysLog>().getPage(pageDto),
                new LambdaQueryWrapper<SysLog>().like(StringUtils.isNotBlank(sysLog.getOperation()), SysLog::getOperation, sysLog.getOperation())
        );*/

        Pageable pageable = Query.getPage(pageDto);

        Page<SysLog> page = logRepository.findAll((root, query, builder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if(StringUtils.isNotBlank(sysLog.getOperation())) {
                predicateList.add(builder.like(root.get("operation"), "%" + sysLog.getOperation() + "%"));
            }
            return builder.and(predicateList.toArray(new Predicate[0]));
        },pageable);

        return new PageUtils(page);
    }
}
