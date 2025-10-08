package com.geosapiens.eucomida.constant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

class SecurityConstantsTest {

    @Test
    void shouldThrowIllegalStateExceptionWhenConstructorIsInvoked() throws Exception {
        Constructor<SecurityConstants> constructor = SecurityConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(Exception.class)
                .satisfies(exception -> {
                    Throwable cause = exception.getCause();
                    assertThat(cause)
                            .isInstanceOf(IllegalStateException.class)
                            .hasMessage(ErrorMessages.UTILITY_CLASS);
                });
    }

    @Test
    void shouldContainCorrectActuatorPath() {
        assertThat(SecurityConstants.ACTUATOR_PATH)
                .isEqualTo("/actuator");
    }

    @Test
    void shouldContainCorrectPublicPaths() {
        assertThat(SecurityConstants.PUBLIC_PATHS)
                .containsExactly(
                        "/actuator/health",
                        "/actuator/info",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                );
    }

    @Test
    void shouldContainCorrectUserMePath() {
        assertThat(SecurityConstants.USER_ME_PATH)
                .isEqualTo("/v1/user/me");
    }
}
