package com.parking.service.mapper;

import com.parking.service.dto.TicketResponse;
import com.parking.service.entity.Ticket;


public class TicketMapper {
    public static TicketResponse toDto(Ticket t) {
        if (t == null) return null;
        Long total = t.getTotalPayment();
        return TicketResponse.builder()
                .id(t.getId())
                .plate(t.getVehicle().getPlate())
                .type(t.getVehicle().getType())
                .entryTime(t.getEntryTime())
                .exitTime(t.getExitTime())
                .paid(t.getPaid())
                .totalPayment(total)
                .build();
    }
}
