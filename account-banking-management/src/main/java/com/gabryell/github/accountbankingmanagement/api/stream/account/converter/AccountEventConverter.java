package com.gabryell.github.accountbankingmanagement.api.stream.account.converter;

import com.gabryell.github.accountbankingmanagement.api.stream.account.model.AccountEvent;
import com.gabryell.github.accountbankingmanagement.core.account.model.AccountBO;
import com.gabryell.github.accountbankingmanagement.exceptionHandler.BusinessException;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AccountEventConverter {

    private static final String INDIVIDUAL_CUSTOMER = "PF";
    private static final String JURIDICAL_CUSTOMER = "PJ";

    public AccountBO toBO(String personType, String documentNumber) {
        return AccountBO.builder()
                .customerDocumentNumber(documentNumber)
                .accountType(toAccountType(personType))
                .build();
    }

    private AccountBO.AccountTypeEnum toAccountType(@NonNull String personType) {
        switch (personType) {
            case INDIVIDUAL_CUSTOMER:
                return AccountBO.AccountTypeEnum.INDIVIDUAL_CUSTOMER;
            case JURIDICAL_CUSTOMER:
                return AccountBO.AccountTypeEnum.JURIDICAL_CUSTOMER;
            default:
                throw new BusinessException("CustomerDocumentType not found with type [" + personType + "]");
        }
    }

    public AccountEvent toEvent(@NonNull AccountBO accountBO, int customerScore) {
        AccountEvent.Customer customer = AccountEvent.Customer.builder()
                .customerDocumentNumber(accountBO.getCustomerDocumentNumber())
                .score(customerScore)
                .build();

        AccountEvent.Account account = AccountEvent.Account.builder()
                .accountNumber(accountBO.getAccountNumber())
                .bankBranch(accountBO.getBankBranch())
                .accountType(AccountEvent.Account.AccountTypeEnum.fromValue(accountBO.getAccountType()))
                .build();

        return AccountEvent.builder()
                .account(account)
                .customer(customer)
                .build();
    }

}
