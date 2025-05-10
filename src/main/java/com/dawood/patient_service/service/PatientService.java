package com.dawood.patient_service.service;

import com.dawood.patient_service.dto.PatientRequestDTO;
import com.dawood.patient_service.dto.PatientResponseDTO;
import com.dawood.patient_service.exceptions.EmailAlreadyExistsException;
import com.dawood.patient_service.exceptions.PatientNotFoundException;
import com.dawood.patient_service.grpc.BillingServiceGrpcClient;
import com.dawood.patient_service.kafka.KafkaProducer;
import com.dawood.patient_service.mapper.PatientMapper;
import com.dawood.patient_service.model.Patient;
import com.dawood.patient_service.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

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
        billingServiceGrpcClient.createBillingAccount(patient.getName(), patient.getId().toString(), patient.getEmail());

        kafkaProducer.sendEvent(patient);

        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID patientId, PatientRequestDTO requestDTO){
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new PatientNotFoundException("Patient does not exists"));

        if(patientRepository.existsByEmailAndIdNot(requestDTO.getEmail(), patientId)){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        patient.setName(requestDTO.getName());
        patient.setAddress(requestDTO.getAddress());
        patient.setEmail(requestDTO.getEmail());
        patient.setDob(LocalDate.parse(requestDTO.getDob()));

        Patient newPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(newPatient);
    }

    public void deletePatient(UUID patientId){
        patientRepository.deleteById(patientId);
    }
}
