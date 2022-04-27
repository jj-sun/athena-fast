package com.athena.jpa;

import com.athena.common.vo.DictModel;
import com.athena.modules.sys.entity.SysRole;
import com.athena.modules.sys.repository.SysDictRepository;
import com.athena.modules.sys.repository.SysPermissionRepository;
import com.athena.modules.sys.repository.SysRoleRepository;
import com.athena.modules.sys.repository.SysUserRepository;
import com.athena.modules.sys.service.SysDictService;
import com.athena.modules.sys.service.SysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @Autowired
    private SysPermissionRepository permissionRepository;

    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private SysDictRepository dictRepository;

    @Autowired
    private SysDictService dictService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysRoleRepository roleRepository;

    @Test
    public void getAllUserPermissionId() {
        List<String> permissionIds =  permissionRepository.queryAllMenuId("1");
        permissionIds.forEach(id -> System.err.println(id + " "));
    }

    @Test
    public void getAllDict() {
        List<DictModel> dictModels = dictService.queryTableDictTextByKeys("sys_dept","dept_name","id", List.of("1485429843877969921","1485429924664459265"));
        System.err.println(dictModels.size());
    }
    /*@Test
    public void getAllDictR() {
        List<DictModel> dictModels = dictRepository.queryTableDictTextByKeys("sys_dept","dept_name","id", List.of("1485429843877969921","1485429924664459265"));
        System.err.println(dictModels.size());
    }*/
    @Test
    public void insertRole() {
        SysRole role = new SysRole();

        role.setRoleName("测试");
        role.setRoleCode("test");
        roleRepository.save(role);
    }

    @Test
    public void updateRole() {
        SysRole role = new SysRole();
        role.setId("1518836788276367360");
        role.setRoleName("测试3");
        role.setRoleCode("test");
        roleRepository.save(role);
    }

}
