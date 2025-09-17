package com.parking.service.service;

import com.parking.service.dto.CreateTicketRequest;
import com.parking.service.dto.TicketResponse;

public interface ParkingService {
    TicketResponse createTicket(CreateTicketRequest request);
    TicketResponse exitVehicle(String plate);
    TicketResponse findActiveTicketByPlate(String plate);
}
