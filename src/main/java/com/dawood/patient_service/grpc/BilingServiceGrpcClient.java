package com.dawood.patient_service.grpc;

import billingService.BillingRequest;
import billingService.BillingRespnse;
import billingService.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String billingAddress,
            @Value("${billing.service.grpc.port:9001}") int billingServerPort
    ){
    log.info("Connecting to Billing Service Service at {}, {}", billingAddress, billingServerPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(billingAddress, billingServerPort)
                .usePlaintext().build();
        billingServiceBlockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingRespnse createBillingAccount (String name, String patientId, String email ){
        BillingRequest request = BillingRequest.newBuilder()
                .setEmail(email)
                .setName(name)
                .setPatientId(patientId)
                .build();

        BillingRespnse response = billingServiceBlockingStub.createBillingAccount(request);
        log.info("Billing service client request connected to the service, {}",response);
        return response;
    }


}
