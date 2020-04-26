package com.gabryell.github.creditmanagement.api.stream.account.producer;

import com.gabryell.github.creditmanagement.api.stream.account.model.AccountEvent;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class CreditEventProducer {

    private static final String TOPIC = "CREDIT-CUSTOMER-CREATION";

    private KafkaTemplate<String, AccountEvent> kafkaTemplate;

    public void notificationCreditCreated(@NonNull AccountEvent accountEvent, Map<String, BigDecimal> creditBasedOnScore) {
        log.info("Adding information about credit customer with number [{}] to event", accountEvent.getCustomer().getCustomerDocumentNumber());
        addCreditInformationToAccountEvent(accountEvent, creditBasedOnScore);
        log.info("Credit information added to event [{}] sending it to topic", accountEvent);

        kafkaTemplate.send(TOPIC, accountEvent);
        log.info("Message sent!");
    }

    private void addCreditInformationToAccountEvent(AccountEvent accountEvent, Map<String, BigDecimal> creditBasedOnScore) {
        BigDecimal overdraftLimit = creditBasedOnScore.get("overdraft");
        BigDecimal creditCardLimit = creditBasedOnScore.get("creditCard");

        AccountEvent.CreditCard creditCard = AccountEvent.CreditCard.builder()
                .limit(creditCardLimit)
                .build();

        AccountEvent.Overdraft overdraft = AccountEvent.Overdraft.builder()
                .limit(overdraftLimit)
                .build();

        AccountEvent.Account account = accountEvent.getAccount();

        account.setCreditCard(creditCard);
        account.setOverdraft(overdraft);
    }

}
