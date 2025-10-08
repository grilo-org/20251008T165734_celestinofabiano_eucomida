package com.geosapiens.eucomida.controller.advice;

import com.geosapiens.eucomida.constant.ErrorMessages;
import com.geosapiens.eucomida.dto.ErrorResponseDto;
import com.geosapiens.eucomida.exception.AuthenticatedUserNotFoundException;
import com.geosapiens.eucomida.exception.CourierNotFoundException;
import com.geosapiens.eucomida.exception.OrderNotFoundException;
import com.geosapiens.eucomida.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String LOG_MESSAGE = "[{}] Exception occurred: {}";

    @ExceptionHandler(AuthenticatedUserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticatedUserNotFoundException(
            AuthenticatedUserNotFoundException ex, HttpServletRequest request) {
        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(
            UserNotFoundException ex, HttpServletRequest request) {
        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFoundException(
            OrderNotFoundException ex, HttpServletRequest request) {
        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(CourierNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCourierNotFoundException(
            CourierNotFoundException ex, HttpServletRequest request) {
        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse(ErrorMessages.VALIDATION_ERROR);

        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), errorMessage, ex);
        return buildErrorResponse(errorMessage, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            NoResourceFoundException ex, HttpServletRequest request) {
        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorResponse(ErrorMessages.RESOURCE_NOT_FOUND_ERROR, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleMessageNotReadableException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorResponse(ErrorMessages.NOT_READABLE_ERROR, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(
            Exception ex, HttpServletRequest request) {
        log.error(LOG_MESSAGE, this.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorResponse(ErrorMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(
            Exception ex, HttpStatus status, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), status, request);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(
            String message, HttpStatus status, HttpServletRequest request) {

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
