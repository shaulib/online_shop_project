package com.example.vmWare_project.configuration;

import com.example.vmWare_project.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final MyBasicAuthenticationEntryPoint authenticationEntryPoint;
    private final ContactService contactService;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    private static final String[] FREE_ACCESS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/owners/register",
            "/customers/register",
            "/h2-console/**"

    };
    private static final String[] SUPER_ADMIN_ACCESS = {
    };
    private static final String[] ADMIN_ACCESS = {
    };
    private static final String[] SUPER_USER_ACCESS = {
            "/add-item-for-sale","owners/get-owner-info"
    };
    private static final String[] USER_ACCESS = {
            "/owners/get-owner-info","/get-all-Inventory","/find-item-by-name","/buy-item","/get-all-Inventory",
            "/customers/edit","/owners/edit"
    };




    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(contactService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(contactService)
                .passwordEncoder(passwordEncoder)
                .and()
                .authenticationProvider(authenticationProvider())
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);
        httpSecurity.authorizeHttpRequests()
                .antMatchers(FREE_ACCESS).permitAll()
                .antMatchers(SUPER_ADMIN_ACCESS).hasAnyRole("SUPER_ADMIN","admin")
                .antMatchers(ADMIN_ACCESS).hasAnyRole("SUPER_ADMIN", "ADMIN","admin")
                .antMatchers(SUPER_USER_ACCESS).hasAnyRole("SUPER_ADMIN", "ADMIN","admin","owner",
                        "SUPER_USER")
                .antMatchers(USER_ACCESS).hasAnyRole("SUPER_ADMIN", "ADMIN","admin","customer","owner",
                        "SUPER_USER", "USER")
                .anyRequest()
                .authenticated();
        httpSecurity.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
