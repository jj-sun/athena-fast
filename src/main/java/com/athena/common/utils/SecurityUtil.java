package com.athena.common.utils;

import com.athena.modules.sys.entity.SysUser;
import com.athena.modules.sys.form.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Mr.sun
 * @date 2022/2/10 20:11
 * @description
 */
public class SecurityUtil {

    public static SysUser getLoginUser() {
        return ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

}
