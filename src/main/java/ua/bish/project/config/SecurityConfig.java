package ua.bish.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.bish.project.data.dao.RoleRepository;
import ua.bish.project.security.jwt.JwtAuthenticationFilter;
import ua.bish.project.security.jwt.JwtTokenAuthenticationManager;

@Configuration
@EnableSpringDataWebSupport
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin()
                .and()
                .addFilterAfter(restTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/rest/*").authenticated();
    }

    @Bean
    public JwtAuthenticationFilter restTokenAuthenticationFilter() {
        JwtAuthenticationFilter restTokenAuthenticationFilter = new JwtAuthenticationFilter();
        restTokenAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return restTokenAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new JwtTokenAuthenticationManager();
    }
}