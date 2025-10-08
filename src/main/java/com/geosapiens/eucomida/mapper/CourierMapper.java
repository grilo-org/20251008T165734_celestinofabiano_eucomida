package com.geosapiens.eucomida.mapper;

import com.geosapiens.eucomida.dto.CourierResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import org.springframework.stereotype.Component;

@Component
public class CourierMapper {

    public CourierResponseDto toDTO(Courier courier) {
        return CourierResponseDto.builder()
                .id(courier.getId())
                .name(courier.getUser().getName())
                .email(courier.getUser().getEmail())
                .vehicleType(courier.getVehicleType())
                .plateNumber(courier.getPlateNumber())
                .build();
    }
}
