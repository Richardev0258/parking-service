package com.parking.service.mapper;

import com.parking.service.dto.TicketResponse;
import com.parking.service.entity.Ticket;
import com.parking.service.entity.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketMapperTest {

    @Test
    void mapNullTicketShouldReturnNull() {
        assertNull(TicketMapper.toDto(null));
    }

    @Test
    void mapValidTicketShouldReturnResponse() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate("ABC123");
        vehicle.setType("CAR");

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setVehicle(vehicle);
        ticket.setPaid(false);

        TicketResponse response = TicketMapper.toDto(ticket);

        assertNotNull(response);
        assertEquals("ABC123", response.getPlate());
        assertFalse(response.getPaid());
    }
}
