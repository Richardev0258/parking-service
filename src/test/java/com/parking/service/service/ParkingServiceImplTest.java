package com.parking.service.service;

import com.parking.service.dto.CreateTicketRequest;
import com.parking.service.entity.Ticket;
import com.parking.service.entity.Vehicle;
import com.parking.service.exception.NotFoundException;
import com.parking.service.repository.TicketRepository;
import com.parking.service.repository.VehicleRepository;
import com.parking.service.service.impl.ParkingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingServiceImplTest {

    private VehicleRepository vehicleRepository;
    private TicketRepository ticketRepository;
    private PaymentService paymentService;
    private ParkingServiceImpl parkingService;

    @BeforeEach
    void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        ticketRepository = mock(TicketRepository.class);
        paymentService = mock(PaymentService.class);
        parkingService = new ParkingServiceImpl(vehicleRepository, ticketRepository, paymentService);
    }

    @Test
    void createTicketSuccess() {
        CreateTicketRequest req = new CreateTicketRequest("AAA111", "CAR");
        Vehicle vehicle = Vehicle.builder().id(1L).plate("AAA111").type("CAR").build();
        when(vehicleRepository.findByPlate("AAA111")).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));

        var res = parkingService.createTicket(req);

        assertEquals("AAA111", res.getPlate());
        assertEquals("CAR", res.getType());
    }

    @Test
    void exitVehicleCalculatesPayment() {
        Vehicle vehicle = Vehicle.builder().id(1L).plate("AAA111").type("CAR").build();
        Ticket ticket = Ticket.builder().id(1L).vehicle(vehicle).entryTime(OffsetDateTime.now().minusMinutes(5)).build();

        when(vehicleRepository.findByPlate("AAA111")).thenReturn(Optional.of(vehicle));
        when(ticketRepository.findByVehicleIdAndExitTimeIsNull(1L)).thenReturn(Optional.of(ticket));
        when(paymentService.calculatePayment(anyString(), any(), any())).thenReturn(500L);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));

        var res = parkingService.exitVehicle("AAA111");

        assertTrue(res.getPaid());
        assertEquals(500L, res.getTotalPayment());
    }

    @Test
    void exitVehicleNotFound() {
        when(vehicleRepository.findByPlate("BBB222")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> parkingService.exitVehicle("BBB222"));
    }
}
