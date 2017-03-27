package ua.bish.project.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class JwtTokenCreationServiceImpl implements JwtTokenCreationService {
    public static final String TOKEN_CREATE_DATE = "token_create_date";
    public static final String TOKEN_EXPIRATION_DATE = "token_expiration_date";
    public static final String CLIENT_TYPE = "client_type";
    public static final String USERNAME = "username";
    public static final String USER = "user";
    public static final String KEY = "key123";

    @Override
    public String getToken(UserDetails userDetails) {
        Map<String, Object> tokenData = new HashMap<>();

        tokenData.put(CLIENT_TYPE, USER);
        tokenData.put(USERNAME, userDetails.getUsername());

        // setup timings
        Calendar calendar = Calendar.getInstance();
        tokenData.put(TOKEN_CREATE_DATE, calendar.getTime());
        calendar.add(Calendar.MINUTE, 10);
        tokenData.put(TOKEN_EXPIRATION_DATE, calendar.getTime());

        return Jwts.builder()
                .setExpiration(calendar.getTime())
                .setClaims(tokenData)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }
}
