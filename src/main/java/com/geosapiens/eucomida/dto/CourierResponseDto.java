package com.geosapiens.eucomida.dto;

import com.geosapiens.eucomida.entity.enums.VehicleType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CourierResponseDto(
        UUID id,
        String name,
        String email,
        VehicleType vehicleType,
        String plateNumber
) {}
