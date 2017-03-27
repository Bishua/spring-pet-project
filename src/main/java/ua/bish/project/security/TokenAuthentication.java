package ua.bish.project.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class TokenAuthentication implements Authentication {
    private Collection<? extends GrantedAuthority> authorities;

    private String token;
    private boolean authenticated;
    private UserDetails principal;

    public TokenAuthentication(String token) {
        this.token = token;
    }

    public TokenAuthentication(String token, Collection<GrantedAuthority> authorities, boolean authenticated, UserDetails principal) {
        this.token = token;
        this.authorities = authorities;
        this.authenticated = authenticated;
        this.principal = principal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getName() {
        return principal != null ? principal.getUsername() : null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    public String getToken() {
        return token;
    }
}
