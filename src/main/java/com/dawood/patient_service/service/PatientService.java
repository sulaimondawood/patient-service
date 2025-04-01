package com.dawood.patient_service.service;

import com.dawood.patient_service.dto.PatientRequestDTO;
import com.dawood.patient_service.dto.PatientResponseDTO;
import com.dawood.patient_service.mapper.PatientMapper;
import com.dawood.patient_service.model.Patient;
import com.dawood.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> findAllPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        Patient newPatient = PatientMapper.toModel(patientRequestDTO);
        Patient patient =patientRepository.save(newPatient);

        return PatientMapper.toDTO(patient);
    }
}
