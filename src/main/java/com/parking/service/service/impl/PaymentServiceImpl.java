package com.parking.service.service.impl;

import com.parking.service.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final long RATE_CAR = 100L;
    private static final long RATE_MOTOR = 80L;

    @Override
    public long calculatePayment(String type, OffsetDateTime entry, OffsetDateTime exit) {
        long minutes = Duration.between(entry, exit).toMinutes();
        if (minutes <= 0) minutes = 1;
        if ("MOTOR".equalsIgnoreCase(type)) {
            return minutes * RATE_MOTOR;
        }
        return minutes * RATE_CAR;
    }
}
