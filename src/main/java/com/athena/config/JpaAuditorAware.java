package com.athena.config;

import com.athena.modules.sys.form.LoginUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.sun
 */
public class JpaAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        LoginUser loginUser = this.getLoginUser();
        return Optional.ofNullable(Objects.isNull(loginUser) ? null : loginUser.getUsername());
    }

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
