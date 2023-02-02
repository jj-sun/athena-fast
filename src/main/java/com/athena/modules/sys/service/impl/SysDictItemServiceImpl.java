package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.QSysDictItem;
import com.athena.modules.sys.entity.QSysUser;
import com.athena.modules.sys.entity.SysDictItem;
import com.athena.modules.sys.repository.SysDictItemRepository;
import com.athena.modules.sys.service.SysDictItemService;
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
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Objects;

/**
 * @author Mr.sun
 * @date 2022/1/19 20:00
 * @description
 */
@Service
public class SysDictItemServiceImpl implements SysDictItemService {

    @Autowired
    private SysDictItemRepository dictItemRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public PageUtils queryPage(SysDictItem dictItem, PageDto pageDto) {
        Pageable pageable = Query.getPage(pageDto);

        QSysDictItem qSysDictItem = QSysDictItem.sysDictItem;
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.isNotBlank(dictItem.getDictId())) {
            builder.and(qSysDictItem.dictId.eq(dictItem.getDictId()));
        }
        if(StringUtils.isNotBlank(dictItem.getItemText())) {
            builder.and(qSysDictItem.itemText.like("%" + dictItem.getItemText() + "%"));
        }
        if(Objects.nonNull(dictItem.getDelFlag())) {
            builder.and(qSysDictItem.delFlag.eq(dictItem.getDelFlag()));
        }
        QueryResults<SysDictItem> page = jpaQueryFactory.selectFrom(qSysDictItem).where(builder).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        return new PageUtils(page);
    }
}
