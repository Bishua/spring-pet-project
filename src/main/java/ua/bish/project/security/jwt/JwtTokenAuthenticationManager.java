package ua.bish.project.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import ua.bish.project.security.TokenAuthentication;

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

        // validate toked data
        Long tokenExpTime = claims.get(JwtTokenCreationServiceImpl.TOKEN_EXPIRATION_DATE, Long.class);
        String username = claims.get(JwtTokenCreationServiceImpl.USERNAME, String.class);
        if (tokenExpTime == null || username == null) {
            throw new AuthenticationServiceException("Invalid token");
        }

        if (new Date().after(new Date(tokenExpTime))) {
            throw new AuthenticationServiceException("Token expired");
        }
        return buildFullTokenAuthentication(auth, username);
    }

    private TokenAuthentication buildFullTokenAuthentication(TokenAuthentication authentication, String username) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (!user.isEnabled()) {
            throw new AuthenticationServiceException("User disabled");
        }
        return new TokenAuthentication(authentication.getToken(), user.getAuthorities(), true, user);
    }
}
