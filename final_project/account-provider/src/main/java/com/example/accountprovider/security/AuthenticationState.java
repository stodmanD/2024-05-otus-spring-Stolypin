package com.example.accountprovider.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Getter
@Setter
public class AuthenticationState extends AbstractAuthenticationToken {

    private String principal;

    private String credentials;

    private Collection<GrantedAuthority> authorities;

    private Map<String, Object> attributes = new HashMap<>();

    public AuthenticationState(Collection<? extends GrantedAuthority> authority) {
        super(authority);
        this.authorities = new HashSet<>(authority);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}