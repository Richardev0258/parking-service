package com.parking.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class ParkingServiceApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> ParkingServiceApplication.main(new String[]{}));
    }

}
