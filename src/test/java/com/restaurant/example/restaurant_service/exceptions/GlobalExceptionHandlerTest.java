package com.restaurant.example.restaurant_service.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleRestaurantNotFoundException() {
        // Given
        String errorMessage = "Restaurant with ID 123 not found";
        RestaurantNotFoundException exception = new RestaurantNotFoundException(errorMessage);

        // When
        ProblemDetail problemDetail = globalExceptionHandler.handleRestaurantNotFoundException(exception);

        // Then
        assertNotNull(problemDetail);
        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
        assertEquals("Restaurant is not found", problemDetail.getTitle());
        assertEquals("No Restaurant Detail was found with the specified Restaurant ID", problemDetail.getDetail());
    }

    @Test
    void testHandleRestaurantNotFoundExceptionWithNullMessage() {
        // Given
        RestaurantNotFoundException exception = new RestaurantNotFoundException();

        // When
        ProblemDetail problemDetail = globalExceptionHandler.handleRestaurantNotFoundException(exception);

        // Then
        assertNotNull(problemDetail);
        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
        assertEquals("Restaurant is not found", problemDetail.getTitle());
        assertEquals("No Restaurant Detail was found with the specified Restaurant ID", problemDetail.getDetail());
    }

    @Test
    void testHandleGenericException() {
        // Given
        String errorMessage = "Something went wrong";
        Exception exception = new Exception(errorMessage);

        // When
        ProblemDetail problemDetail = globalExceptionHandler.handleGenericException(exception);

        // Then
        assertNotNull(problemDetail);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problemDetail.getStatus());
        assertEquals("Unexpected Error", problemDetail.getTitle());
        assertEquals("System has encountered an unexpected error", problemDetail.getDetail());
    }

    @Test
    void testHandleGenericExceptionWithNullMessage() {
        // Given
        Exception exception = new Exception();

        // When
        ProblemDetail problemDetail = globalExceptionHandler.handleGenericException(exception);

        // Then
        assertNotNull(problemDetail);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problemDetail.getStatus());
        assertEquals("Unexpected Error", problemDetail.getTitle());
        assertEquals("System has encountered an unexpected error", problemDetail.getDetail());
    }

    @Test
    void testHandleTypeMismatchException() {
        // Given
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getMessage()).thenReturn("Failed to convert value");

        // When
        ProblemDetail problemDetail = globalExceptionHandler.handleTypeMismatchException(exception);

        // Then
        assertNotNull(problemDetail);
        assertEquals(HttpStatus.BAD_REQUEST.value(), problemDetail.getStatus());
        assertEquals("Invalid Request Parameter", problemDetail.getTitle());
        assertEquals("The provided ID is not in the correct format.", problemDetail.getDetail());
    }

    @Test
    void testHandleRuntimeException() {
        // Given
        String errorMessage = "Runtime error occurred";
        RuntimeException exception = new RuntimeException(errorMessage);

        // When
        ProblemDetail problemDetail = globalExceptionHandler.handleGenericException(exception);

        // Then
        assertNotNull(problemDetail);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problemDetail.getStatus());
        assertEquals("Unexpected Error", problemDetail.getTitle());
        assertEquals("System has encountered an unexpected error", problemDetail.getDetail());
    }
}