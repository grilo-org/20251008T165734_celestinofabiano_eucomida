package com.geosapiens.eucomida.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.dto.CourierResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.entity.enums.VehicleType;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourierMapperTest {

    private CourierMapper courierMapper;

    @BeforeEach
    void setUp() {
        courierMapper = new CourierMapper();
    }

    @Test
    void shouldMapCourierToCourierResponseDto() {
        UUID id = UUID.randomUUID();
        User user = new User("John Doe", "john.doe@example.com");
        Courier courier = new Courier();
        courier.setId(id);
        courier.setUser(user);
        courier.setVehicleType(VehicleType.MOTORCYCLE);
        courier.setPlateNumber("ABC1234");

        CourierResponseDto responseDto = courierMapper.toDTO(courier);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.id()).isEqualTo(id);
        assertThat(responseDto.name()).isEqualTo("John Doe");
        assertThat(responseDto.email()).isEqualTo("john.doe@example.com");
        assertThat(responseDto.vehicleType()).isEqualTo(VehicleType.MOTORCYCLE);
        assertThat(responseDto.plateNumber()).isEqualTo("ABC1234");
    }
}
