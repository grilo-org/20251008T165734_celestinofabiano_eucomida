package com.geosapiens.eucomida.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.geosapiens.eucomida.dto.CourierResponseDto;
import com.geosapiens.eucomida.entity.Courier;
import com.geosapiens.eucomida.exception.CourierNotFoundException;
import com.geosapiens.eucomida.mapper.CourierMapper;
import com.geosapiens.eucomida.repository.CourierRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourierServiceImplTest {

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private CourierMapper courierMapper;

    @InjectMocks
    private CourierServiceImpl courierService;

    private UUID courierId;

    @BeforeEach
    void setUp() {
        courierId = UUID.randomUUID();
    }

    @Test
    void shouldReturnCourierDtoWhenCourierExists() {
        Courier courier = new Courier();
        CourierResponseDto responseDto = new CourierResponseDto(courierId, "Courier Name", "email@example.com", null, "ABC-1234");
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courier));
        when(courierMapper.toDTO(courier)).thenReturn(responseDto);

        Optional<CourierResponseDto> result = courierService.findDtoById(courierId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(responseDto);
    }

    @Test
    void shouldReturnEmptyWhenCourierDoesNotExist() {
        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        Optional<CourierResponseDto> result = courierService.findDtoById(courierId);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnCourierWhenCourierExists() {
        Courier courier = new Courier();
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courier));

        Optional<Courier> result = courierService.findById(courierId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(courier);
    }

    @Test
    void shouldReturnEmptyWhenCourierNotFound() {
        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        Optional<Courier> result = courierService.findById(courierId);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnCourierWhenIdExists() {
        Courier courier = new Courier();
        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courier));

        Courier result = courierService.findIfIdExists(courierId);

        assertThat(result).isEqualTo(courier);
    }

    @Test
    void shouldReturnNullWhenIdIsNull() {
        Courier result = courierService.findIfIdExists(null);
        assertThat(result).isNull();
    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courierService.findIfIdExists(courierId))
                .isInstanceOf(CourierNotFoundException.class);
    }
}
