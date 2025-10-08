package com.geosapiens.eucomida.security;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtAuthenticationConverter extends JwtAuthenticationConverter {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String CLAIM_ROLES = "roles";

    public CustomJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix(ROLE_PREFIX);
        grantedAuthoritiesConverter.setAuthoritiesClaimName(CLAIM_ROLES);

        this.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    }
}
