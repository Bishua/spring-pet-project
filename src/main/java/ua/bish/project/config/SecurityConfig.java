package ua.bish.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.bish.project.data.dao.RoleRepository;
import ua.bish.project.security.jwt.JwtTokenAuthenticationManager;

@Configuration
@EnableSpringDataWebSupport
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private RoleRepository roleRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new JwtTokenAuthenticationManager();
    }
}