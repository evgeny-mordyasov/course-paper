package ru.gold.ordance.course.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.gold.ordance.course.common.constants.Role;
import ru.gold.ordance.course.web.service.authorization.jwt.JwtConfigurer;
import ru.gold.ordance.course.web.service.authorization.jwt.JwtProvider;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.RolePrivilege;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtConfigurer jwtConfigurer;

    public WebSecurityConfiguration(JwtProvider jwtProvider) {
        this.jwtConfigurer = new JwtConfigurer(jwtProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureBase(http);
        configureRequests(http);
        http.apply(jwtConfigurer);
    }

    private void configureBase(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void configureRequests(HttpSecurity http) throws Exception {
        var configurer = http.authorizeRequests();

        RolePrivilege.list()
                .forEach(rolePrivilege -> {
                    Role role = rolePrivilege.getEndpointPermit().getRole();

                    rolePrivilege.getEndpointPermit().getEndpointRules()
                            .forEach(rule -> {
                                var conf = (rule.isAllMethods())
                                        ? configurer.mvcMatchers(rule.getUrl())
                                        : configurer.mvcMatchers(rule.getHttpMethod(), rule.getUrl());

                                if (role.equals(Role.ANONYMOUS)) {
                                    conf.permitAll();
                                } else {
                                    conf.hasAuthority(role.name());
                                }
                            });
                });

        configurer.anyRequest().authenticated();
    }
}
