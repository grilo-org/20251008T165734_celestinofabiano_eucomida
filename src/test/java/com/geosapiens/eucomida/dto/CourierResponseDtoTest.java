package com.geosapiens.eucomida.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.entity.enums.VehicleType;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CourierResponseDtoTest {

    @Test
    void shouldCreateCourierResponseDto() {
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "johndoe@example.com";
        VehicleType vehicleType = VehicleType.MOTORCYCLE;
        String plateNumber = "ABC1D23";

        CourierResponseDto dto = CourierResponseDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .vehicleType(vehicleType)
                .plateNumber(plateNumber)
                .build();

        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.name()).isEqualTo(name);
        assertThat(dto.email()).isEqualTo(email);
        assertThat(dto.vehicleType()).isEqualTo(vehicleType);
        assertThat(dto.plateNumber()).isEqualTo(plateNumber);
    }

    @Test
    void shouldValidateEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        CourierResponseDto dto1 = CourierResponseDto.builder()
                .id(id)
                .name("John Doe")
                .email("johndoe@example.com")
                .vehicleType(VehicleType.MOTORCYCLE)
                .plateNumber("ABC1D23")
                .build();

        CourierResponseDto dto2 = CourierResponseDto.builder()
                .id(id)
                .name("John Doe")
                .email("johndoe@example.com")
                .vehicleType(VehicleType.MOTORCYCLE)
                .plateNumber("ABC1D23")
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}
