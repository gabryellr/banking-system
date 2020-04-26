package com.gabryell.github.accountbankingmanagement.api.stream.credit;

import com.gabryell.github.accountbankingmanagement.api.stream.account.model.AccountEvent;
import com.gabryell.github.accountbankingmanagement.core.account.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigInteger.ZERO;

@Component
@Slf4j
@AllArgsConstructor
public class CreditEventConsumer {

    private static final String TOPIC = "CREDIT-CUSTOMER-CREATION";

    private AccountService accountService;

    @KafkaListener(topics = TOPIC)
    public void consumer(@Payload AccountEvent accountEvent) {
        log.info("AccountEvent received with body [{}]", accountEvent);
        AccountEvent.Account account = accountEvent.getAccount();
        AccountEvent.CreditCard creditCard = account.getCreditCard();
        AccountEvent.Overdraft overdraft = account.getOverdraft();
        AccountEvent.Customer customer = accountEvent.getCustomer();

        BigDecimal creditCarLimit = new BigDecimal(ZERO);
        BigDecimal overdraftLimit = new BigDecimal(ZERO);

        if (overdraft != null) {
            if (overdraft.getLimit() != null) {
                overdraftLimit = overdraft.getLimit();
            }
        }

        if (creditCard != null) {
            if (creditCard.getLimit() != null) {
                creditCarLimit = creditCard.getLimit();
            }
        }

        log.info("Updating account with customer document number [{}] with limits", customer.getCustomerDocumentNumber());
        accountService.updateAccountWithCredit(creditCarLimit, overdraftLimit, customer.getCustomerDocumentNumber());
    }

}
