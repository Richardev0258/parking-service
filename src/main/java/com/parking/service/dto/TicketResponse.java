package com.parking.service.dto;

import lombok.*;
import java.time.OffsetDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String plate;
    private String type;
    private OffsetDateTime entryTime;
    private OffsetDateTime exitTime;
    private Boolean paid;
    private Long totalPayment;
}
