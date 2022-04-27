package com.athena.modules.sys.service;

import com.athena.common.base.dto.PageDto;
import com.athena.common.utils.PageUtils;
import com.athena.modules.sys.entity.SysDictItem;

/**
 * @author Mr.sun
 * @date 2022/1/19 19:58
 * @description
 */
public interface SysDictItemService {

    PageUtils queryPage(SysDictItem dictItem, PageDto pageDto);

}
