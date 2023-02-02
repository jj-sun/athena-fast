package com.athena.modules.sys.service.impl;

import com.athena.common.base.tree.BaseTree;
import com.athena.common.constant.Constant;
import com.athena.common.utils.TreeUtils;
import com.athena.modules.sys.entity.SysDept;
import com.athena.modules.sys.repository.SysDeptRepository;
import com.athena.modules.sys.service.SysDeptService;
import com.athena.modules.sys.vo.SysDeptTree;
import com.athena.modules.sys.vo.SysMenuTree;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mr.sun
 * @date 2022/1/24 9:01
 * @description
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptRepository deptRepository;

    @Override
    public List<SysDept> deptTreeList() {
        List<SysDept> sysDeptList = deptRepository.findAll();
        if(!CollectionUtils.isEmpty(sysDeptList)) {
            List<SysDept> roots = sysDeptList.stream().filter(root -> root.getParentId().equals(Constant.TREE_ROOT)).sorted(Comparator.comparing(SysDept::getSortOrder)).collect(Collectors.toList());
            buildTree(roots, sysDeptList);
            return roots;
        }
        return sysDeptList;
    }
    private void buildTree(List<SysDept> roots, List<SysDept> all) {
        if(CollectionUtils.isEmpty(roots)) {
            return;
        }
        roots.forEach(root -> {
            List<SysDept> childrenList = all.stream().filter(tree -> tree.getParentId().equals(root.getId())).sorted(Comparator.comparing(SysDept::getSortOrder)).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(childrenList)) {
                root.setChildren(childrenList);
                buildTree(childrenList, all);
            }
        });
    }

    @Override
    public List<SysDeptTree> treeSelect() {
        List<SysDept> sysDeptList = deptRepository.findAll();
        List<SysDeptTree> menuTreeList = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(sysDeptList)) {
            sysDeptList.forEach(sysDept -> menuTreeList.add(new SysDeptTree(sysDept)));
            List<SysDeptTree> root = menuTreeList.stream().filter(sysMenuTree -> sysMenuTree.getParentKey().equals(Constant.TREE_ROOT)).sorted(Comparator.comparing(BaseTree::getOrderNum)).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(root)) {
                TreeUtils.buildTree(root, menuTreeList);
                return root;
            }
        }
        return null;
    }

    @Override
    public boolean deleteBatch(List<String> ids) {
        deptRepository.deleteAllByIdInBatch(ids);
        return true;
    }
}
