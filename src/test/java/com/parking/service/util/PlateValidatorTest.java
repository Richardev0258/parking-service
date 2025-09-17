package com.parking.service.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlateValidatorTest {

    private final PlateValidator validator = new PlateValidator();

    @Test
    void validPlateShouldReturnTrue() {
        assertTrue(validator.isValid("ABC123"));
    }

    @Test
    void nullPlateShouldReturnFalse() {
        assertFalse(validator.isValid(null));
    }

    @Test
    void emptyPlateShouldReturnFalse() {
        assertFalse(validator.isValid(""));
    }

    @Test
    void invalidFormatShouldReturnFalse() {
        assertFalse(validator.isValid("12AB34"));
    }
}
