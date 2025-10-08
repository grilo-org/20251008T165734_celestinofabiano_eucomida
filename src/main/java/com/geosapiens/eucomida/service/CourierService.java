package com.geosapiens.eucomida.service;

import com.geosapiens.eucomida.dto.CourierResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import java.util.Optional;
import java.util.UUID;

public interface CourierService {

    Optional<CourierResponseDto> findDtoById(UUID id);

    Optional<Courier> findById(UUID id);

    Courier findIfIdExists(UUID id);
}
