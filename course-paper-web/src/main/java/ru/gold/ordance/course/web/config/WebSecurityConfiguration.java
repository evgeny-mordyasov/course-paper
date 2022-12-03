package ru.gold.ordance.course.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gold.ordance.course.common.constants.Role;
import ru.gold.ordance.course.web.config.properties.JwtProperties;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.Authority;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointPermit;
import ru.gold.ordance.course.web.service.authorization.jwt.JwtConfigurer;
import ru.gold.ordance.course.web.service.authorization.jwt.JwtProvider;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.EndpointRule;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService service;
    private final JwtProperties jwtProp;

    public WebSecurityConfiguration(UserDetailsService service, JwtProperties jwtProp) {
        this.service = service;
        this.jwtProp = jwtProp;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtProvider jwtProvider(UserDetailsService service, JwtProperties jwtProp, AuthenticationManager manager) {
        return new JwtProvider(service, jwtProp, manager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureBase(http);
        configureRequests(http);
        http.apply(new JwtConfigurer(jwtProvider(service, jwtProp, authenticationManagerBean())));
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

        Arrays.stream(EndpointPermit.values()).forEach(endpointPermit -> {
            setPermit(endpointPermit, rule -> {
                var conf = resolveHttpMethod(configurer, rule);

                if (endpointPermit.getRole().equals(Role.ANONYMOUS)) {
                    conf.permitAll();
                } else {
                    conf.hasAuthority(endpointPermit.getRole().name());
                }
            });
        });

        configurer.anyRequest().authenticated();
    }

    private void setPermit(EndpointPermit endpoint, Authority action) {
        Arrays.stream(endpoint.getRules())
                .forEach(action::apply);
    }

    private ExpressionUrlAuthorizationConfigurer<HttpSecurity>.MvcMatchersAuthorizedUrl resolveHttpMethod(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer,
                                                                                                          EndpointRule rule) {
        if (rule.isAllMethods()) {
            return configurer.mvcMatchers(rule.getUrl());
        } else {
            return configurer.mvcMatchers(rule.getHttpMethod(), rule.getUrl());
        }
    }
}
