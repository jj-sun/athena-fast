/*
package com.athena.config;

import com.athena.common.constant.Constant;
import com.athena.common.utils.DateUtils;
import com.athena.modules.sys.form.LoginUser;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

*/
/**
 * @author Mr.sun
 * @date 2021/12/20 15:06
 * @description
 *//*

@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = this.getLoginUser();
        String ctimeStr = "ctime";
        if (Objects.isNull(this.getFieldValByName(ctimeStr, metaObject))) {
            this.setFieldValByName(ctimeStr, DateUtils.now(), metaObject);
        }
        String mtimeStr = "mtime";
        if (Objects.isNull(this.getFieldValByName(mtimeStr, metaObject))) {
            this.setFieldValByName(mtimeStr, DateUtils.now(), metaObject);
        }
        String creator = "creator";
        if (Objects.isNull(this.getFieldValByName(creator, metaObject)) && !Objects.isNull(loginUser)) {
            this.setFieldValByName(creator, loginUser.getUser().getId(), metaObject);
        }
        String editor = "editor";
        if (Objects.isNull(this.getFieldValByName(editor, metaObject)) && !Objects.isNull(loginUser)) {
            this.setFieldValByName(editor, loginUser.getUser().getId(), metaObject);
        }
        String delFlag = "delFlag";
        if (Objects.isNull(this.getFieldValByName(delFlag, metaObject))) {
            this.setFieldValByName(delFlag, Constant.DelFlag.DEL_FLAG_0.getValue(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser loginUser = this.getLoginUser();
        String mtimeStr = "mtime";
        String editor = "editor";

        this.setFieldValByName(mtimeStr, DateUtils.now(), metaObject);
        if (Objects.isNull(this.getFieldValByName(editor, metaObject)) && !Objects.isNull(loginUser)) {
            this.setFieldValByName(editor, loginUser.getUser().getId(), metaObject);
        }
    }

    */
/**
     * 自动提交时可能无法获取用户信息。
     * @return 用户信息
     *//*

    private LoginUser getLoginUser() {
        LoginUser sysUser;
        try {
            sysUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null ? (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;
        } catch (Exception e) {
            sysUser = null;
        }
        return sysUser;
    }
}
*/
