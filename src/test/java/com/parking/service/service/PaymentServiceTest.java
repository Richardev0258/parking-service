package com.parking.service.service;

import com.parking.service.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentServiceTest {

    private final PaymentService paymentService = new PaymentServiceImpl();

    @Test
    void calculatePaymentForCar() {
        OffsetDateTime entry = OffsetDateTime.now().minusMinutes(10);
        OffsetDateTime exit = OffsetDateTime.now();
        long total = paymentService.calculatePayment("CAR", entry, exit);
        assertEquals(1000L, total);
    }

    @Test
    void calculatePaymentForMotor() {
        OffsetDateTime entry = OffsetDateTime.now().minusMinutes(5);
        OffsetDateTime exit = OffsetDateTime.now();
        long total = paymentService.calculatePayment("MOTOR", entry, exit);
        assertEquals(400L, total);
    }
}
