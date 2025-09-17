package com.parking.service.service.impl;

import com.parking.service.dto.CreateTicketRequest;
import com.parking.service.dto.TicketResponse;
import com.parking.service.entity.Ticket;
import com.parking.service.entity.Vehicle;
import com.parking.service.exception.NotFoundException;
import com.parking.service.mapper.TicketMapper;
import com.parking.service.repository.TicketRepository;
import com.parking.service.repository.VehicleRepository;
import com.parking.service.service.ParkingService;
import com.parking.service.service.PaymentService;
import com.parking.service.util.PlateValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {

    private final VehicleRepository vehicleRepository;
    private final TicketRepository ticketRepository;
    private final PaymentService paymentService;

    public ParkingServiceImpl(VehicleRepository vehicleRepository, TicketRepository ticketRepository, PaymentService paymentService) {
        this.vehicleRepository = vehicleRepository;
        this.ticketRepository = ticketRepository;
        this.paymentService = paymentService;
    }

    @Override
    public TicketResponse createTicket(CreateTicketRequest request) {
        String plate = request.getPlate().trim().toUpperCase();
        if (!PlateValidator.isValid(plate)) throw new IllegalArgumentException("Invalid plate format");
        Vehicle vehicle = vehicleRepository.findByPlate(plate).orElseGet(() -> {
            Vehicle v = Vehicle.builder().plate(plate).type(request.getType()).build();
            return vehicleRepository.save(v);
        });
        ticketRepository.findByVehicleIdAndExitTimeIsNull(vehicle.getId()).ifPresent(t -> {
            throw new IllegalStateException("Vehicle already inside");
        });
        Ticket ticket = Ticket.builder()
                .vehicle(vehicle)
                .entryTime(OffsetDateTime.now())
                .paid(false)
                .totalPayment(null)
                .build();
        Ticket saved = ticketRepository.save(ticket);
        return TicketMapper.toDto(saved);
    }

    @Override
    public TicketResponse exitVehicle(String plate) {
        String p = plate.trim().toUpperCase();
        Vehicle vehicle = vehicleRepository.findByPlate(p).orElseThrow(() -> new NotFoundException("Vehicle not found"));
        Ticket ticket = ticketRepository.findByVehicleIdAndExitTimeIsNull(vehicle.getId()).orElseThrow(() -> new NotFoundException("Active ticket not found"));

        OffsetDateTime now = OffsetDateTime.now();
        ticket.setExitTime(now);

        long total = paymentService.calculatePayment(vehicle.getType(), ticket.getEntryTime(), now);

        ticket.setPaid(true);
        ticket.setTotalPayment(total);

        Ticket saved = ticketRepository.save(ticket);
        return TicketMapper.toDto(saved);
    }

    @Override
    public TicketResponse findActiveTicketByPlate(String plate) {
        String p = plate.trim().toUpperCase();
        Vehicle vehicle = vehicleRepository.findByPlate(p).orElseThrow(() -> new NotFoundException("Vehicle not found"));
        Ticket ticket = ticketRepository.findByVehicleIdAndExitTimeIsNull(vehicle.getId()).orElseThrow(() -> new NotFoundException("Active ticket not found"));
        return TicketMapper.toDto(ticket);
    }
}
