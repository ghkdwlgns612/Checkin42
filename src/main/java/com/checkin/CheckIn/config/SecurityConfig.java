package com.checkin.CheckIn.config;

import com.checkin.CheckIn.utils.resource.CustomOAuth2Provider;
import com.checkin.CheckIn.utils.resource.OAuth42Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth42Resource oAuth42Resource;

    public SecurityConfig(OAuth42Resource oAuth42Resource) {
        this.oAuth42Resource = oAuth42Resource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter jwtFilter = new JWTFilter();
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .antMatchers("/cluster", "/swagger-ui/**/", "/login/callback/**", "/user/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        http.addFilterAfter(jwtFilter, LogoutFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-resources/**", "/v2/api-docs");
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrationList = new ArrayList<>();
        registrationList.add(CustomOAuth2Provider.FOURTYTWO.getBuilder("42")
                .clientId(oAuth42Resource.getClientId())
                .clientSecret(oAuth42Resource.getClientSecret())
                .redirectUri(oAuth42Resource.getRedirectUri())
                .authorizationGrantType(AUTHORIZATION_CODE).build());
        return new InMemoryClientRegistrationRepository(registrationList);
    }
}
