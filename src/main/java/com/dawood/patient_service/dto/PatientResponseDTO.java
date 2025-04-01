package com.dawood.patient_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {
    private String id;

    private String name;

    private String email;

    private String address;

    private String dob;

}
