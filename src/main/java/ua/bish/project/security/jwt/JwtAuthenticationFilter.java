package ua.bish.project.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import ua.bish.project.security.TokenAuthentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter responsible of user authentication using tokens.
 * If token received it passed to {@link JwtTokenAuthenticationManager}.
 * token can be received from header or as request parameter
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String FILTER_PATH = "/rest/**";

    public JwtAuthenticationFilter() {
        super(FILTER_PATH);

        // authentication successful
        setAuthenticationSuccessHandler((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // redirect to requested path
            request.getRequestDispatcher(request.getServletPath() + request.getPathInfo()).forward(request, response);
        });

        // authentication failed
        setAuthenticationFailureHandler((request, response, authenticationException) ->
                response.getOutputStream().print(authenticationException.getMessage()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String token = request.getHeader("token");
        if (token == null)
            token = request.getParameter("token");
        if (token == null) {
            TokenAuthentication authentication = new TokenAuthentication(null);
            authentication.setAuthenticated(false);
            return authentication;
        }
        return getAuthenticationManager().authenticate(new TokenAuthentication(token));
    }
}