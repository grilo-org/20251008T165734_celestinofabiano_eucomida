package com.geosapiens.eucomida.service.impl;

import com.geosapiens.eucomida.dto.UserRequestDto;
import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.exception.AuthenticatedUserNotFoundException;
import com.geosapiens.eucomida.exception.UserNotFoundException;
import com.geosapiens.eucomida.service.AuthenticationService;
import com.geosapiens.eucomida.service.UserService;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    public static final String CLAIM_NAME = "name";
    public static final String CLAIM_EMAIL = "email";

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserResponseDto> findCurrentUserDto() {
        return getAuthentication()
                .flatMap(this::processAuthenticatedUser);
    }

    @Override
    public User findCurrentUser() {
        String email = getCurrentUserEmail()
                .orElseThrow(AuthenticatedUserNotFoundException::new);

        return userService.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<String> getCurrentUserEmail() {
        return getClaim(CLAIM_EMAIL);
    }

    @Override
    public Optional<String> getCurrentToken() {
        return getToken();
    }

    @Override
    public Optional<UserResponseDto> processAuthenticatedUser(Authentication authentication) {
        String name = getClaim(CLAIM_NAME).orElse(null);
        String email = getClaim(CLAIM_EMAIL).orElse(null);
        return createOrGetUser(name, email);
    }

    @Override
    public Optional<UserResponseDto> createOrGetUser(String name, String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(userService.getOrCreate(
                UserRequestDto.builder()
                        .name(name != null ? name : email)
                        .email(email)
                        .build()
        ));
    }

    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    public static Optional<String> getClaim(String claimKey) {
        return getAuthentication()
                .map(Authentication::getPrincipal)
                .flatMap(principal -> switch (principal) {
                    case OidcUser oidcUser -> CLAIM_EMAIL.equals(claimKey)
                            ? Optional.ofNullable(oidcUser.getEmail())
                            : Optional.ofNullable(oidcUser.getFullName());
                    case Jwt jwt -> Optional.ofNullable(jwt.getClaimAsString(claimKey));
                    default -> Optional.empty();
                });
    }

    public static Optional<String> getToken() {
        return getAuthentication().flatMap(authentication -> switch (authentication) {
            case JwtAuthenticationToken jwtAuth -> Optional.of(jwtAuth.getToken().getTokenValue());
            case OAuth2AuthenticationToken oauth when oauth.getPrincipal() instanceof OidcUser oidcUser ->
                    Optional.ofNullable(oidcUser.getIdToken())
                            .flatMap(idToken -> Optional.ofNullable(idToken.getTokenValue()));
            default -> Optional.empty();
        });
    }

}
