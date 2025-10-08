package com.geosapiens.eucomida.security;

import com.geosapiens.eucomida.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomJwtAuthenticationConverter jwtAuthenticationConverter;
    private final String defaultSuccessUrl;

    public SecurityConfig(CustomJwtAuthenticationConverter jwtAuthenticationConverter,
            @Value("${api.path}") String apiPath) {
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
        this.defaultSuccessUrl = apiPath + SecurityConstants.USER_ME_PATH;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        configureAuthorization(http);
        configureOAuth2Login(http);
        configureOAuth2ResourceServer(http);
        return http.build();
    }

    private void configureAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(SecurityConstants.PUBLIC_PATHS).permitAll()
                .anyRequest().authenticated()
        );
    }

    private void configureOAuth2Login(HttpSecurity http) throws Exception {
        http.oauth2Login(login -> login.defaultSuccessUrl(defaultSuccessUrl, true));
    }

    private void configureOAuth2ResourceServer(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
        );
    }
}
