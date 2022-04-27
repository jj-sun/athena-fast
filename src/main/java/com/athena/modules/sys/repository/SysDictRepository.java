package com.athena.modules.sys.repository;

import com.athena.common.base.repository.BaseRepository;
import com.athena.common.vo.DictModel;
import com.athena.common.vo.DictModelMany;
import com.athena.modules.sys.entity.SysDict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Mr.sun
 * @date 2022/1/19 19:57
 * @description
 */
public interface SysDictRepository extends BaseRepository<SysDict, String> {

    /**
     * 15 字典表的 翻译，可批量
     *
     * @param table
     * @param text
     * @param code
     * @param keys  多个用逗号分割
     * @return  此处无法使用动态表名
     */
    @Deprecated
    @Query(nativeQuery = true, value = "select :#{#text} as 'text', :#{#code} as 'value' from :#{#table} where :#{#code} IN :keys")
    List<DictModel> queryTableDictTextByKeys(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keys") List<String> keys);

    /**
     * 可通过多个字典code查询翻译文本
     *
     * @param dictCodeList 多个字典code
     * @param keys         数据列表
     * @return
     */
    @Query("""
            SELECT dict.dictCode AS dictCode, item.itemText AS text, item.itemValue AS value
            FROM SysDictItem AS item
            INNER JOIN SysDict AS dict ON dict.id = item.dictId
            WHERE dict.dictCode IN :dictCodeList AND item.itemValue IN :keys
            """)
    List<DictModelMany> queryManyDictByKeys(@Param("dictCodeList") List<String> dictCodeList, @Param("keys") List<String> keys);

}
