package com.parking.service.repository;

import com.parking.service.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByPlate(String plate);
}
