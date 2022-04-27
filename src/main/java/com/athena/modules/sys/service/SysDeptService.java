package com.athena.modules.sys.service;

import com.athena.modules.sys.entity.SysDept;
import com.athena.modules.sys.vo.SysDeptTree;

import java.util.List;

/**
 * @author Mr.sun
 * @date 2022/1/24 9:01
 * @description
 */
public interface SysDeptService {
    /**
     * 部门树形列表
     * @return
     */
    List<SysDept> deptTreeList();

    /**
     * 树形选择
     * @return
     */
    List<SysDeptTree> treeSelect();

    boolean deleteBatch(List<String> ids);
}
