package com.dawood.patient_service.kafka;

import com.dawood.patient_service.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafKaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafKaTemplate){
        this.kafKaTemplate = kafKaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setPatientEmail(patient.getEmail())
                .setPatientName(patient.getName())
                .setEventType("PATIENT_EVENT")
                .build();

        try {
            kafKaTemplate.send("patient", event.toByteArray());
            log.info("Kafka producer sent PATIENT EVENT");
        }catch (Exception e){
            log.error("Failed to send PATIENT_EVENT {}", event);
        }
    }

}
