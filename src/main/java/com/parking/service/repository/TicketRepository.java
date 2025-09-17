package com.parking.service.repository;

import com.parking.service.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByVehicleIdAndExitTimeIsNull(Long vehicleId);
}
