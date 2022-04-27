package com.athena.modules.sys.service;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.common.vo.DictModel;
import com.athena.modules.sys.entity.SysDict;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.sun
 * @date 2022/1/19 19:58
 * @description
 */
public interface SysDictService {

    PageUtils queryPage(SysDict dict, PageDto pageDto);

    boolean deleteEntity(String id);

    /**
     * 通过查询指定table的 text code key 获取字典值，可批量查询
     *
     * @param table
     * @param text
     * @param code
     * @param keys
     * @return
     */
    List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> keys);

    /**
     * 可通过多个字典code查询翻译文本
     * @param dictCodeList 多个字典code
     * @param keys 数据列表
     * @return
     */
    Map<String, List<DictModel>> queryManyDictByKeys(List<String> dictCodeList, List<String> keys);
}
