package com.dawood.patient_service.service;

import com.dawood.patient_service.dto.PatientRequestDTO;
import com.dawood.patient_service.dto.PatientResponseDTO;
import com.dawood.patient_service.exceptions.EmailAlreadyExistsException;
import com.dawood.patient_service.exceptions.PatientNotFoundException;
import com.dawood.patient_service.mapper.PatientMapper;
import com.dawood.patient_service.model.Patient;
import com.dawood.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Patient newPatient = PatientMapper.toModel(patientRequestDTO);
        Patient patient =patientRepository.save(newPatient);

        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID patientId, PatientRequestDTO requestDTO){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new PatientNotFoundException("Patient does not exists"));

        if(patientRepository.existsByEmail(requestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        patient.setName(requestDTO.getName());
        patient.setAddress(requestDTO.getAddress());
        patient.setEmail(requestDTO.getEmail());
        patient.setDob(LocalDate.parse(requestDTO.getDob()));

        Patient newPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(newPatient);
    }
}
