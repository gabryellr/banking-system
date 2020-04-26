package com.gabryell.github.accountbankingmanagement.api.stream.customer.consumer;

import com.gabryell.github.accountbankingmanagement.api.stream.account.converter.AccountEventConverter;
import com.gabryell.github.accountbankingmanagement.api.stream.account.producer.AccountEventProducer;
import com.gabryell.github.accountbankingmanagement.api.stream.customer.model.CustomerEvent;
import com.gabryell.github.accountbankingmanagement.core.account.model.AccountBO;
import com.gabryell.github.accountbankingmanagement.core.account.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class CustomerEventConsumer {

    private static final String TOPIC = "CUSTOMER-CREATION";

    private AccountEventConverter accountEventConverter;
    private AccountService accountService;
    private AccountEventProducer accountEventProducer;

    @KafkaListener(topics = TOPIC)
    public void consumer(@Payload CustomerEvent customerEvent) {
        log.info("CustomerEvent [{}] was received on topic", customerEvent);

        CustomerEvent.Customer customer = customerEvent.getCustomer();
        CustomerEvent.Document document = customer.getDocument();

        String personType = document.getPersonType().name();
        String documentNumber = document.getDocumentNumber();

        AccountBO accountBO = accountEventConverter.toBO(personType, documentNumber);

        log.info("Creating information to banking account to the customer with document number [{}]", documentNumber);
        accountService.createBankingAccountInformation(accountBO);

        accountEventProducer.sendToCreatedCredit(accountBO, customer.getScore());
    }
}
