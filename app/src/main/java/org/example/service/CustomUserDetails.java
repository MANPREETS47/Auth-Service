package org.example.service;

import org.example.entities.userinfo;
import org.example.entities.userroles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomUserDetails extends userinfo implements UserDetails{
    

    private String username;

    private String password;

    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(userinfo user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (userroles role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        // Assuming you have a method to convert user roles to authorities
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
