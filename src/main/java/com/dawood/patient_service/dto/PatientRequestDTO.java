package com.dawood.patient_service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PatientRequestDTO {
    @NotNull(message = "Name is required")
    @Size(min = 3)
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull( message="Address is required")
    private String address;

    @NotBlank(message = "Date of birth is required")
    private String dob;

    @NotBlank(message = "Register date is required")
    private String registeredDate;
}

