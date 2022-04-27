package com.athena.modules.sys.form;

import com.athena.modules.sys.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Mr.sun
 * @date 2021/12/14 11:31
 * @description
 */
@Data
public class LoginUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = -3519083953528868241L;

    private SysUser user;

    private List<? extends GrantedAuthority> authorityList;

    private Set<String> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return getUser().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
