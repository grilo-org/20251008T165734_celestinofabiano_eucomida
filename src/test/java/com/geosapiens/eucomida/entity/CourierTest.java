package com.geosapiens.eucomida.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.geosapiens.eucomida.constant.ValidationConstants;
import com.geosapiens.eucomida.entity.enums.VehicleType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourierTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldCreateCourier() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Courier courier = new Courier(user, VehicleType.MOTORCYCLE, "ABC1D23");

        assertThat(courier.getUser()).isEqualTo(user);
        assertThat(courier.getVehicleType()).isEqualTo(VehicleType.MOTORCYCLE);
        assertThat(courier.getPlateNumber()).isEqualTo("ABC1D23");
    }

    @Test
    void shouldInvalidateNullUser() {
        Courier courier = new Courier(null, VehicleType.BICYCLE, "ABC1D23");

        Set<ConstraintViolation<Courier>> violations = validator.validate(courier);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("O entregador deve estar associado a um usuário");
    }

    @Test
    void shouldInvalidateNullVehicleType() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Courier courier = new Courier(user, null, "ABC1D23");

        Set<ConstraintViolation<Courier>> violations = validator.validate(courier);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.COURIER_VEHICLE_TYPE_REQUIRED);
    }

    @Test
    void shouldInvalidateInvalidPlateNumber() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Courier courier = new Courier(user, VehicleType.CAR, "1234");

        Set<ConstraintViolation<Courier>> violations = validator.validate(courier);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly(ValidationConstants.COURIER_PLATE_NUMBER_MESSAGE);
    }

    @Test
    void shouldValidateValidPlateNumber() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Courier courier = new Courier(user, VehicleType.CAR, "ABC1D23");

        Set<ConstraintViolation<Courier>> violations = validator.validate(courier);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(UUID.randomUUID());

        Courier courier1 = new Courier(user, VehicleType.MOTORCYCLE, "ABC1D23");
        courier1.setId(id);

        Courier courier2 = new Courier(user, VehicleType.MOTORCYCLE, "ABC1D23");
        courier2.setId(id);

        assertThat(courier1).isEqualTo(courier2);
        assertThat(courier1.hashCode()).isEqualTo(courier2.hashCode());
    }

    @Test
    void shouldNotBeEqualIfIdsAreDifferent() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Courier courier1 = new Courier(user, VehicleType.BICYCLE, "ABC1D23");
        courier1.setId(UUID.randomUUID());

        Courier courier2 = new Courier(user, VehicleType.BICYCLE, "ABC1D23");
        courier2.setId(UUID.randomUUID());

        assertThat(courier1).isNotEqualTo(courier2);
    }

    @Test
    void shouldNotBeEqualIfIdIsNull() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Courier courier1 = new Courier(user, VehicleType.BICYCLE, "ABC1D23");
        courier1.setId(null);

        Courier courier2 = new Courier(user, VehicleType.BICYCLE, "ABC1D23");
        courier2.setId(UUID.randomUUID());

        assertThat(courier1).isNotEqualTo(courier2);
    }
}
