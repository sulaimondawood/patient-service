package com.dawood.patient_service.controller;

import com.dawood.patient_service.dto.PatientRequestDTO;
import com.dawood.patient_service.dto.PatientResponseDTO;
import com.dawood.patient_service.dto.validators.PatientCreateRequestGroup;
import com.dawood.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Api for managing patient")
public class PatientController {
    private final PatientService patientService;

    @Operation(summary = "Get patients")
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> findAllPatients(){
       return ResponseEntity.ok().body( patientService.findAllPatients());
    }

    @PostMapping
    @Operation(summary = "Create Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, PatientCreateRequestGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO){
        return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO));
    }

    @PutMapping("/{patientId}")
    @Operation(summary = "Update Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDTO requestDTO,
                                                            @PathVariable UUID patientId){
        return ResponseEntity.ok().body(patientService.updatePatient(patientId,requestDTO));
    }

    @DeleteMapping("/{patientId}")
    @Operation(summary = "Delete Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID patientId){
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }

}
