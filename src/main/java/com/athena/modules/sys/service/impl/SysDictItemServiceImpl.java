package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.modules.sys.entity.SysDictItem;
import com.athena.modules.sys.repository.SysDictItemRepository;
import com.athena.modules.sys.service.SysDictItemService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
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

    @Override
    public PageUtils queryPage(SysDictItem dictItem, PageDto pageDto) {
        /*IPage<SysDictItem> page = this.page(
                new Query<SysDictItem>().getPage(pageDto),
                new LambdaQueryWrapper<SysDictItem>()
                        .eq(StringUtils.isNotBlank(dictItem.getDictId()), SysDictItem::getDictId, dictItem.getDictId())
                        .like(StringUtils.isNotBlank(dictItem.getItemText()), SysDictItem::getItemText, dictItem.getItemText())
                        .eq(Objects.nonNull(dictItem.getDelFlag()), SysDictItem::getDelFlag, dictItem.getDelFlag())
        );*/

        Pageable pageable = Query.getPage(pageDto);
        Page<SysDictItem> page = dictItemRepository.findAll((root, query, builder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if(StringUtils.isNotBlank(dictItem.getDictId())) {
                predicateList.add(builder.equal(root.get("dictId"), dictItem.getDictId()));
            }
            if(StringUtils.isNotBlank(dictItem.getItemText())) {
                predicateList.add(builder.like(root.get("itemText"), dictItem.getItemText()));
            }
            if(Objects.nonNull(dictItem.getDelFlag())) {
                predicateList.add(builder.equal(root.get("delFlag"), dictItem.getDelFlag()));
            }
            return builder.and(predicateList.toArray(predicateList.toArray(new Predicate[0])));
        }, pageable);

        return new PageUtils(page);
    }
}
