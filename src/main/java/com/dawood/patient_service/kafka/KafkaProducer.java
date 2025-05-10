package com.dawood.patient_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafKaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafKaTemplate){
        this.kafKaTemplate = kafKaTemplate;
    }

}
