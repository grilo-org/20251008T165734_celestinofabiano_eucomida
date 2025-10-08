package com.geosapiens.eucomida.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

class CustomJwtAuthenticationConverterTest {

    private CustomJwtAuthenticationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CustomJwtAuthenticationConverter();
    }

    @Test
    void shouldConvertJwtToAuthenticationWithRoles() {
        Jwt jwt = createJwtWithRoles(List.of("ADMIN", "USER"));
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) converter.convert(jwt);

        assertThat(authentication).isNotNull();
        assertThat(authentication.getAuthorities()).extracting("authority")
                .containsExactlyInAnyOrder("ROLE_ADMIN", "ROLE_USER");
    }

    @Test
    void shouldConvertJwtToAuthenticationWithoutRoles() {
        Jwt jwt = createJwtWithRoles(List.of());
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) converter.convert(jwt);

        assertThat(authentication).isNotNull();
        assertThat(authentication.getAuthorities()).isEmpty();
    }

    private Jwt createJwtWithRoles(List<String> roles) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .claim("roles", roles)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        return Jwt.withTokenValue("token")
                .headers(headers -> headers.put("alg", "RS256"))
                .claims(claimsMap -> claimsMap.putAll(claims.getClaims()))
                .build();
    }
}
