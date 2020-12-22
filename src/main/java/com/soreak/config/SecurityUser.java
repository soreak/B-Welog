package com.soreak.config;

import com.soreak.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @program: welog
 * @author: soreak
 * @description:
 * @create: 2020-12-22 13:04
 **/
public class SecurityUser extends User {
    private String password;
    private final String username = super.getUsername();
    private final Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) super.getAuthorities();
    private final boolean accountNonExpired = super.isAccountNonExpired();
    private final boolean accountNonLocked=super.isAccountNonLocked();
    private final boolean credentialsNonExpired = super.isCredentialsNonExpired();
    private final boolean enabled = super.isEnabled();

    UserEntity userEntity;

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities,UserEntity userEntity) {
        super(username, password, authorities);
        this.userEntity=userEntity;
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(" [");
        sb.append("Username=").append(this.username).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Enabled=").append(this.enabled).append(", ");
        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
        sb.append("credentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
        sb.append("userEntity=").append(this.userEntity).append(", ");
        sb.append("Granted Authorities=").append(this.authorities).append("]");
        return sb.toString();
    }






}
