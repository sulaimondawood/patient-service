package com.dawood.patient_service.mapper;

import com.dawood.patient_service.dto.PatientRequestDTO;
import com.dawood.patient_service.dto.PatientResponseDTO;
import com.dawood.patient_service.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO responseDTO = new PatientResponseDTO();

        responseDTO.setId(patient.getId().toString());
        responseDTO.setName(patient.getName());
        responseDTO.setAddress(patient.getAddress());
        responseDTO.setEmail(patient.getEmail());
        responseDTO.setDob(patient.getDob().toString());

        return responseDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDob(LocalDate.parse(patientRequestDTO.getDob()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));

        return patient;
    }
}
