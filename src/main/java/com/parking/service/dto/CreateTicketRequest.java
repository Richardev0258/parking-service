package com.parking.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTicketRequest {
    @NotBlank
    private String plate;


    @NotBlank
    private String type;
}
