package com.parking.service.exception;

import lombok.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
