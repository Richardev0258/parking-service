package com.parking.service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;


@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


    @Column(name = "entry_time", nullable = false)
    private OffsetDateTime entryTime;


    @Column(name = "exit_time")
    private OffsetDateTime exitTime;


    @Column(name = "paid", nullable = false)
    private Boolean paid = false;


    @Column(name = "total_payment")
    private Long totalPayment;
}
