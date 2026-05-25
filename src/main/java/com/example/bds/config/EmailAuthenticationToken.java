package com.example.bds.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;


public class EmailAuthenticationToken extends AbstractAuthenticationToken {

    private final Object credentials;
    private final Object principal;



    public EmailAuthenticationToken(String email, String password) {
        super(null);
        setAuthenticated(false);
        this.credentials = password ;
        this.principal = email;
    }
    public EmailAuthenticationToken(Object credentials, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        setAuthenticated(true);
        this.credentials = credentials;
        this.principal = principal;
    }
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}
