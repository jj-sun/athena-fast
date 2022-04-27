package com.athena.modules.sys.repository;

import com.athena.common.base.repository.BaseRepository;
import com.athena.modules.sys.entity.SysDictItem;

/**
 * @author Mr.sun
 * @date 2022/1/19 19:57
 * @description
 */
public interface SysDictItemRepository extends BaseRepository<SysDictItem, String> {

    void deleteByDictId(String dictId);

}
