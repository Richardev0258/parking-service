package com.parking.service.service;

import java.time.OffsetDateTime;

public interface PaymentService {
    long calculatePayment(String type, OffsetDateTime entry, OffsetDateTime exit);
}
