package com.parking.service.controller;

import com.parking.service.dto.CreateTicketRequest;
import com.parking.service.dto.TicketResponse;
import com.parking.service.exception.NotFoundException;
import com.parking.service.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/entry")
    public ResponseEntity<TicketResponse> entry(@Valid @RequestBody CreateTicketRequest request) {
        return ResponseEntity.ok(parkingService.createTicket(request));
    }

    @PostMapping("/exit/{plate}")
    public ResponseEntity<TicketResponse> exit(@PathVariable String plate) {
        return parkingService.exitVehicle(plate)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Vehicle with plate " + plate + " not found"));
    }

    @GetMapping("/active/{plate}")
    public ResponseEntity<TicketResponse> active(@PathVariable String plate) {
        return ResponseEntity.ok(parkingService.findActiveTicketByPlate(plate));
    }
}
