package com.geosapiens.eucomida.controller.advice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.geosapiens.eucomida.constant.ErrorMessages;
import com.geosapiens.eucomida.dto.ErrorResponseDto;
import com.geosapiens.eucomida.exception.AuthenticatedUserNotFoundException;
import com.geosapiens.eucomida.exception.CourierNotFoundException;
import com.geosapiens.eucomida.exception.OrderNotFoundException;
import com.geosapiens.eucomida.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpInputMessage httpInputMessage;

    @Mock
    private HttpMethod httpMethod;

    private static final String TEST_PATH = "/test";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void shouldHandleAuthenticatedUserNotFoundException() {
        when(request.getRequestURI()).thenReturn(TEST_PATH);
        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleAuthenticatedUserNotFoundException(
                new AuthenticatedUserNotFoundException(), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(
                ErrorMessages.AUTHENTICATED_USER_NOT_FOUND);
    }

    @Test
    void shouldHandleUserNotFoundException() {
        when(request.getRequestURI()).thenReturn(TEST_PATH);
        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleUserNotFoundException(
                new UserNotFoundException(), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(
                ErrorMessages.USER_NOT_FOUND);
    }

    @Test
    void shouldHandleOrderNotFoundException() {
        when(request.getRequestURI()).thenReturn(TEST_PATH);
        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleOrderNotFoundException(
                new OrderNotFoundException(), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(ErrorMessages.ORDER_NOT_FOUND);
    }

    @Test
    void shouldHandleCourierNotFoundException() {
        when(request.getRequestURI()).thenReturn(TEST_PATH);
        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleCourierNotFoundException(
                new CourierNotFoundException(), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(ErrorMessages.COURIER_NOT_FOUND);
    }

    @Test
    void shouldHandleResourceNotFoundException() {
        when(request.getRequestURI()).thenReturn(TEST_PATH);

        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleResourceNotFoundException(
                new NoResourceFoundException(httpMethod, TEST_PATH), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(
                ErrorMessages.RESOURCE_NOT_FOUND_ERROR);
    }

    @Test
    void shouldHandleMessageNotReadableException() throws IOException {
        when(request.getRequestURI()).thenReturn(TEST_PATH);
        when(httpInputMessage.getBody()).thenThrow(new UnsupportedOperationException());

        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleMessageNotReadableException(
                new HttpMessageNotReadableException(TEST_PATH, httpInputMessage), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(ErrorMessages.NOT_READABLE_ERROR);
    }

    @Test
    void shouldHandleValidationException() {
        when(request.getRequestURI()).thenReturn(TEST_PATH);
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(
                mock(org.springframework.validation.BindingResult.class));

        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleValidationException(
                exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(ErrorMessages.VALIDATION_ERROR);
    }

    @Test
    void shouldHandleMethodArgumentTypeMismatchException() {
        String errorMessage = "Failed to convert value of type 'String' to required type 'Integer'";

        when(request.getRequestURI()).thenReturn(TEST_PATH);

        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getMessage()).thenReturn(errorMessage);

        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleMethodArgumentTypeMismatchException(
                exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void shouldHandleGenericException() {
        when(request.getRequestURI()).thenReturn(TEST_PATH);
        ResponseEntity<ErrorResponseDto> response = globalExceptionHandler.handleGenericException(
                new Exception("Unexpected error"), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(ErrorMessages.INTERNAL_SERVER_ERROR);
    }
}
