package com.dawood.patient_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "patient")
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotNull( message="Address is required")
    private String address;

    @NotNull
    private LocalDate dob;
    @NotNull
    private LocalDate registeredDate;

    @LastModifiedDate
    private LocalDate updated;
    @CreatedDate
    private LocalDate createdAt;
}
