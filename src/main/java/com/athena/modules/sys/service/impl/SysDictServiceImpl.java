package com.athena.modules.sys.service.impl;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.utils.Query;
import com.athena.common.vo.DictModel;
import com.athena.common.vo.DictModelMany;
import com.athena.modules.sys.entity.QSysDict;
import com.athena.modules.sys.entity.SysDict;
import com.athena.modules.sys.repository.SysDictItemRepository;
import com.athena.modules.sys.repository.SysDictRepository;
import com.athena.modules.sys.service.SysDictService;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.sun
 * @date 2022/1/19 19:59
 * @description
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictRepository dictRepository;

    @Autowired
    private SysDictItemRepository dictItemRepository;

    @Resource
    private EntityManager entityManager;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public PageUtils queryPage(SysDict dict, PageDto pageDto) {

        Pageable pageable = Query.getPage(pageDto);

        QSysDict qSysDict = QSysDict.sysDict;

        BooleanBuilder builder = new BooleanBuilder();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(dict.getDictName())) {
            builder.and(qSysDict.dictName.like("%" + dict.getDictName() + "%"));
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(dict.getDictCode())) {
            builder.and(qSysDict.dictCode.eq(dict.getDictCode()));
        }

        QueryResults<SysDict> page = jpaQueryFactory.selectFrom(qSysDict).where(builder).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        return new PageUtils(page);
    }

    @Override
    public boolean deleteEntity(String id) {
        dictItemRepository.deleteByDictId(id);
        dictRepository.deleteById(id);
        return true;
    }

    /**
     * 字典表的 翻译，可批量
     * @param table
     * @param text
     * @param code
     * @param keys
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> keys) {
        List<DictModel> result = Lists.newArrayList();
        if(CollectionUtils.isEmpty(keys)) {
            return result;
        }
        String sql = "SELECT %s AS 'text', %s AS 'value' FROM %s WHERE %s IN :keys".formatted(text, code, table,code);
        return entityManager.createNativeQuery(sql, DictModel.class).setParameter("keys", keys).getResultList();
        // returning dtos
//        Session session = entityManager.unwrap(Session.class);
//        return session.createSQLQuery(sql).setParameter("keys", keys).setResultTransformer(Transformers.aliasToBean(DictModel.class)).getResultList();
    }

    @Override
    public Map<String, List<DictModel>> queryManyDictByKeys(List<String> dictCodeList, List<String> keys) {
        var list = dictRepository.queryManyDictByKeys(dictCodeList, keys);
        Map<String, List<DictModel>> dictMap = new HashMap<>(100);
        for (DictModelMany dict : list) {
            List<DictModel> dictItemList = dictMap.computeIfAbsent(dict.getDictCode(), i -> new ArrayList<>());
            dictItemList.add(new DictModel(dict.getValue(), dict.getText()));
        }
        return dictMap;
    }
}
