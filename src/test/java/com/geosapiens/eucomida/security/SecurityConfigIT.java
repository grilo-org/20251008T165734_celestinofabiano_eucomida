package com.geosapiens.eucomida.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SecurityConfigIT {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private HttpSecurity httpSecurity;

    @Test
    void shouldCreateSecurityFilterChain() throws Exception {
        SecurityFilterChain filterChain = securityConfig.securityFilterChain(httpSecurity);
        assertThat(filterChain).isNotNull();
    }
}
