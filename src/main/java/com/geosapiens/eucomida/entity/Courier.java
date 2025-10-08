package com.geosapiens.eucomida.entity;

import com.geosapiens.eucomida.constant.ValidationConstants;
import com.geosapiens.eucomida.entity.enums.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "couriers")
public class Courier extends BaseEntity {

    @NotNull(message = "O entregador deve estar associado a um usuário")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_courier_user"))
    private User user;

    @NotNull(message = ValidationConstants.COURIER_VEHICLE_TYPE_REQUIRED)
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 30)
    private VehicleType vehicleType;

    @Pattern(regexp = ValidationConstants.COURIER_PLATE_NUMBER_REGEX, message = ValidationConstants.COURIER_PLATE_NUMBER_MESSAGE)
    @Column(name = "plate_number", length = 30)
    private String plateNumber;
}
