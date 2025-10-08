package com.geosapiens.eucomida;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

class EuComidaApplicationTest {

    @Test
    void shouldCallSpringApplicationRunWithCorrectArguments() {
        String[] args = {"--spring.profiles.active=test"};
        try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
            EuComidaApplication.main(args);

            mockedStatic.verify(() -> SpringApplication.run(EuComidaApplication.class, args));
        }
    }

}
