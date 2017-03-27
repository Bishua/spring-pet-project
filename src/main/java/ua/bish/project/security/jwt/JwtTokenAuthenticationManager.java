package ua.bish.project.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ua.bish.project.security.TokenAuthentication;

import java.util.Collection;
import java.util.Date;


public class JwtTokenAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            if (authentication instanceof TokenAuthentication) {
                return processAuthentication((TokenAuthentication) authentication);
            }
            authentication.setAuthenticated(false);
            return authentication;

        } catch (Exception ex) {
            if (ex instanceof AuthenticationServiceException)
                throw ex;
        }
        return null;
    }

    private TokenAuthentication processAuthentication(TokenAuthentication auth) throws AuthenticationException {

        DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parser().setSigningKey(JwtTokenCreationServiceImpl.KEY)
                    .parse(auth.getToken()).getBody();
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Token corrupted");
        }

        if (new Date().after(new Date(claims.get(JwtTokenCreationServiceImpl.TOKEN_EXPIRATION_DATE, Long.class)))) {
            throw new AuthenticationServiceException("Token expired date error");
        }
        return buildFullTokenAuthentication(auth, claims);
    }

    private TokenAuthentication buildFullTokenAuthentication(TokenAuthentication authentication, DefaultClaims claims) {
        User user = (User) userDetailsService.loadUserByUsername(claims.get("USERNAME", String.class));
        if (user.isEnabled()) {
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            return new TokenAuthentication(authentication.getToken(), authorities, true, user);
        } else {
            throw new AuthenticationServiceException("User disabled");
        }
    }
}
