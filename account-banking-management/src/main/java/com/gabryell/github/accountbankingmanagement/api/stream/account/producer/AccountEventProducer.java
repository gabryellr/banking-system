package com.gabryell.github.accountbankingmanagement.api.stream.account.producer;

import com.gabryell.github.accountbankingmanagement.api.stream.account.converter.AccountEventConverter;
import com.gabryell.github.accountbankingmanagement.api.stream.account.model.AccountEvent;
import com.gabryell.github.accountbankingmanagement.core.account.model.AccountBO;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class AccountEventProducer {

    private static final String TOPIC = "BANKING-ACCOUNT-CREATION";

    private AccountEventConverter accountEventConverter;
    private KafkaTemplate kafkaTemplate;

    public void sendToCreatedCredit(@NonNull AccountBO accountBO, int customerScore) {

        log.info("Converting AccountBO to AccountEvent");
        AccountEvent accountEvent = accountEventConverter.toEvent(accountBO, customerScore);
        log.info("AccountBO has been converted to AccountEvent with body: [{}]", accountEvent);

        log.info("Sending AccountEvent to topic with body: [{}]", accountEvent);
        kafkaTemplate.send(TOPIC, accountEvent);
        log.info("CustomerEvent sent to topic");
    }

}