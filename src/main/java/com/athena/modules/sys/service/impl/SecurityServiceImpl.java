package com.athena.modules.sys.service.impl;

import com.athena.common.constant.Constant;
import com.athena.modules.sys.entity.SysPermission;
import com.athena.modules.sys.repository.SysPermissionRepository;
import com.athena.modules.sys.repository.SysUserRepository;
import com.athena.modules.sys.service.SecurityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Mr.sun
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private SysPermissionRepository sysPermissionRepository;
    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public Set<String> getUserPermissions(String username) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(username.equals(Constant.SUPER_ADMIN)){
            List<SysPermission> menuList = sysPermissionRepository.findAll();
            permsList = new ArrayList<>(menuList.size());
            for(SysPermission menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserRepository.queryAllPerms(username);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }
}
