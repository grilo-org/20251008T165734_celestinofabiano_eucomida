package com.geosapiens.eucomida.controller;

import static com.geosapiens.eucomida.constant.SwaggerConstants.AUTH_USER_200;
import static com.geosapiens.eucomida.constant.SwaggerConstants.AUTH_USER_DESCRIPTION;
import static com.geosapiens.eucomida.constant.SwaggerConstants.AUTH_USER_SUMMARY;
import static com.geosapiens.eucomida.constant.SwaggerConstants.NOT_FOUND_404;
import static com.geosapiens.eucomida.constant.SwaggerConstants.UNAUTHORIZED_401;

import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.path}/v1/user")
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = AUTH_USER_SUMMARY, description = AUTH_USER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = AUTH_USER_200),
            @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401),
            @ApiResponse(responseCode = "404", description = NOT_FOUND_404)
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> findCurrentUser() {
        return authenticationService.findCurrentUserDto()
                .map(user -> {
                    ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
                    createAuthorizationHeader().ifPresent(responseBuilder::headers);
                    return responseBuilder.body(user);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Optional<HttpHeaders> createAuthorizationHeader() {
        return authenticationService.getCurrentToken()
                .map(token -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set(HttpHeaders.AUTHORIZATION, token);
                    return headers;
                });
    }

}
