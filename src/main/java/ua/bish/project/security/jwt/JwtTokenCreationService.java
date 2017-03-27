package ua.bish.project.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenCreationService {
    String getToken(UserDetails userDetails);
}
