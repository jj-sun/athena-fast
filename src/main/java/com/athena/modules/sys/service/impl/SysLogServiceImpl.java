package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.QSysLog;
import com.athena.modules.sys.entity.SysLog;
import com.athena.modules.sys.repository.SysLogRepository;
import com.athena.modules.sys.service.SysLogService;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public PageUtils queryPage(SysLog sysLog, PageDto pageDto) {

        Pageable pageable = Query.getPage(pageDto);

        QSysLog qSysLog = QSysLog.sysLog;
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.isNotBlank(sysLog.getOperation())) {
            builder.and(qSysLog.operation.like("%" + sysLog.getOperation() + "%"));
        }

        QueryResults<SysLog> page = jpaQueryFactory.selectFrom(qSysLog).where(builder).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        return new PageUtils(page);
    }
}
