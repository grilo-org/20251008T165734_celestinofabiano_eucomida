package com.geosapiens.eucomida.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplStaticTest {

    @Test
    void shouldGetAuthenticationWhenPresent() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            Optional<Authentication> result = AuthenticationServiceImpl.getAuthentication();
            assertThat(result).isPresent().contains(authentication);
        }
    }

    @Test
    void shouldGetAuthenticationWhenNotPresent() {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            Optional<Authentication> result = AuthenticationServiceImpl.getAuthentication();
            assertThat(result).isEmpty();
        }
    }

    @Test
    void shouldGetClaimWhenOidcUser() {
        OidcUser oidcUser = mock(OidcUser.class);
        when(oidcUser.getEmail()).thenReturn("test@example.com");
        when(oidcUser.getFullName()).thenReturn("Test User");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getClaim(AuthenticationServiceImpl.CLAIM_EMAIL)).contains(
                    "test@example.com");
            assertThat(AuthenticationServiceImpl.getClaim(AuthenticationServiceImpl.CLAIM_NAME)).contains(
                    "Test User");
        }
    }

    @Test
    void shouldGetClaimWhenJwt() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("custom_claim")).thenReturn("custom_value");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getClaim("custom_claim")).contains("custom_value");
        }
    }

    @Test
    void shouldNotGetClaimWhenInvalidPrincipal() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("InvalidPrincipal");

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getClaim("any_claim")).isEmpty();
        }
    }

    @Test
    void shouldGetTokenWhenJwtAuthenticationToken() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("jwt-token");
        JwtAuthenticationToken authentication = mock(JwtAuthenticationToken.class);
        when(authentication.getToken()).thenReturn(jwt);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getToken()).contains("jwt-token");
        }
    }

    @Test
    void shouldGetTokenWhenOAuth2AuthenticationTokenWithOidcUser() {
        OidcUser oidcUser = mock(OidcUser.class);
        when(oidcUser.getIdToken()).thenReturn(mock(OidcIdToken.class));
        when(oidcUser.getIdToken().getTokenValue()).thenReturn("oidc-token");
        OAuth2AuthenticationToken authentication = mock(OAuth2AuthenticationToken.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getToken()).contains("oidc-token");
        }
    }

    @Test
    void shouldNotGetTokenWhenInvalidAuthentication() {
        Authentication authentication = mock(Authentication.class);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getToken()).isEmpty();
        }
    }

    @Test
    void shouldNotGetTokenWhenOAuth2AuthenticationTokenHasOidcUserWithoutIdToken() {
        OidcUser oidcUser = mock(OidcUser.class);
        when(oidcUser.getIdToken()).thenReturn(null);
        OAuth2AuthenticationToken authentication = mock(OAuth2AuthenticationToken.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getToken()).isEmpty();
        }
    }

    @Test
    void shouldNotGetTokenWhenOAuth2AuthenticationTokenHasOidcUserWithNullTokenValue() {
        OidcUser oidcUser = mock(OidcUser.class);
        OidcIdToken oidcIdToken = mock(OidcIdToken.class);
        when(oidcIdToken.getTokenValue()).thenReturn(null);
        when(oidcUser.getIdToken()).thenReturn(oidcIdToken);
        OAuth2AuthenticationToken authentication = mock(OAuth2AuthenticationToken.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getToken()).isEmpty();
        }
    }

    @Test
    void shouldGetTokenWhenOAuth2AuthenticationTokenWithNonOidcUserPrincipal() {
        OAuth2AuthenticationToken authentication = mock(OAuth2AuthenticationToken.class);
        OAuth2User nonOidcPrincipal = mock(OAuth2User.class);
        when(authentication.getPrincipal()).thenReturn(nonOidcPrincipal);

        try (MockedStatic<SecurityContextHolder> contextHolder = mockStatic(
                SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            contextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            assertThat(AuthenticationServiceImpl.getToken()).isEmpty();
        }
    }
}
