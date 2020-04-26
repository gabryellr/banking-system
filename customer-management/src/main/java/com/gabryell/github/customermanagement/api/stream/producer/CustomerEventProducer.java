package com.gabryell.github.customermanagement.api.stream.producer;

import com.gabryell.github.customermanagement.api.controller.model.CustomerOutputDTO;
import com.gabryell.github.customermanagement.api.stream.converter.CustomerEventConverter;
import com.gabryell.github.customermanagement.api.stream.model.CustomerEvent;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class CustomerEventProducer {

    private static final String TOPIC = "CUSTOMER-CREATION";
    private CustomerEventConverter customerEventConverter;
    private KafkaTemplate<String, CustomerEvent> kafkaTemplate;

    public void communicateCreatedCustomer(@NonNull CustomerOutputDTO customerOutputDTO) {

        log.info("Converting CustomerOutputDTO to CustomerEvent");
        CustomerEvent customerEvent = customerEventConverter.toEvent(customerOutputDTO);
        log.info("CustomerOutputDTO has been converted to CustomerEvent with body: [{}]", customerEvent);

        log.info("Sending customer event to topic with body: [{}]", customerEvent);
        kafkaTemplate.send(TOPIC, customerEvent);
        log.info("CustomerEvent sent to topic");
    }
}
