package com.parking.service.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleNotFound() {
        NotFoundException ex = new NotFoundException("Resource not found");

        ResponseEntity<ApiError> response = handler.handleNotFound(ex);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("Not Found", response.getBody().getError());
        assertEquals("Resource not found", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHandleBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid input");

        ResponseEntity<ApiError> response = handler.handleBadRequest(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Bad Request", response.getBody().getError());
        assertEquals("Invalid input", response.getBody().getMessage());
    }

    @Test
    void testHandleConflict() {
        IllegalStateException ex = new IllegalStateException("Conflict detected");

        ResponseEntity<ApiError> response = handler.handleConflict(ex);

        assertEquals(409, response.getStatusCode().value());
        assertEquals("Conflict", response.getBody().getError());
        assertEquals("Conflict detected", response.getBody().getMessage());
    }

    @Test
    void testHandleValidation() {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("objectName", "field", "must not be blank"));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ApiError> response = handler.handleValidation(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation Error", response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains("field"));
    }

    @Test
    void testHandleAll() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<ApiError> response = handler.handleAll(ex);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Internal Error", response.getBody().getError());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }
}