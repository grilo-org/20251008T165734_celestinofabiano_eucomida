package com.geosapiens.eucomida.dto;

import com.geosapiens.eucomida.constant.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserRequestDto(
        @NotBlank(message = ValidationConstants.USER_NAME_REQUIRED)
        String name,

        @NotBlank(message = ValidationConstants.USER_EMAIL_REQUIRED)
        @Email(message = ValidationConstants.USER_EMAIL_INVALID)
        String email
) {

}
