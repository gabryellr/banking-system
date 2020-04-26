package com.gabryell.github.creditmanagement.api.stream.account.consumer;

import com.gabryell.github.creditmanagement.api.stream.account.model.AccountEvent;
import com.gabryell.github.creditmanagement.api.stream.account.producer.CreditEventProducer;
import com.gabryell.github.creditmanagement.core.service.CreditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class AccountEventConsumer {

    private static final String TOPIC = "BANKING-ACCOUNT-CREATION";
    private CreditService creditService;
    private CreditEventProducer creditEventProducer;

    @KafkaListener(topics = TOPIC)
    public void consumer(@Payload AccountEvent accountEvent) {
        log.info("account received with body [{}] from topic", accountEvent);

        AccountEvent.Customer customer = accountEvent.getCustomer();
        int customerScore = customer.getScore();

        log.info("Generating credit to customer [{}] and score [{}]", customer.getCustomerDocumentNumber(), customerScore);
        Map<String, BigDecimal> creditBasedOnScore = creditService.createCreditBasedOnScore(customerScore);
        log.info("Credit has been created with values [{}] to customer with document number [{}]", creditBasedOnScore, customer.getCustomerDocumentNumber());

        creditEventProducer.notificationCreditCreated(accountEvent, creditBasedOnScore);
    }

}
