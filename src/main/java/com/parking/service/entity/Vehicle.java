package com.parking.service.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "plate", nullable = false)
    private String plate;


    @Column(name = "type", nullable = false)
    private String type;
}
