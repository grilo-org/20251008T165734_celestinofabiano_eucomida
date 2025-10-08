package com.geosapiens.eucomida.service.impl;

import com.geosapiens.eucomida.dto.CourierResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import com.geosapiens.eucomida.exception.CourierNotFoundException;
import com.geosapiens.eucomida.mapper.CourierMapper;
import com.geosapiens.eucomida.repository.CourierRepository;
import com.geosapiens.eucomida.service.CourierService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;

    public CourierServiceImpl(CourierRepository courierRepository, CourierMapper courierMapper) {
        this.courierRepository = courierRepository;
        this.courierMapper = courierMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourierResponseDto> findDtoById(UUID id) {
        return courierRepository.findById(id)
                .map(courierMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Courier> findById(UUID id) {
        return courierRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Courier findIfIdExists(UUID id) {
        if (id == null) {
            return null;
        }
        return findById(id)
                .orElseThrow(CourierNotFoundException::new);

    }
}
