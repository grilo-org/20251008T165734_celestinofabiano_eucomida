package com.geosapiens.eucomida.entity;

import com.geosapiens.eucomida.constant.ValidationConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank(message = ValidationConstants.USER_NAME_REQUIRED)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = ValidationConstants.USER_EMAIL_REQUIRED)
    @Email(message = ValidationConstants.USER_EMAIL_INVALID)
    @Column(nullable = false, unique = true)
    private String email;
}
