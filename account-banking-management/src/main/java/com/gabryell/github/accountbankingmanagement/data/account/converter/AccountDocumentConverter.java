package com.gabryell.github.accountbankingmanagement.data.account.converter;

import com.gabryell.github.accountbankingmanagement.core.account.model.AccountBO;
import com.gabryell.github.accountbankingmanagement.data.account.model.AccountDocument;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class AccountDocumentConverter {

    public AccountDocument toDocument(@NonNull AccountBO accountBO) {
        AccountDocument.Account account = AccountDocument.Account.builder()
                .number(accountBO.getAccountNumber())
                .type(toAccountTypeDocument(accountBO.getAccountType()))
                .bankBranch(accountBO.getBankBranch())
                .build();

        AccountDocument.Customer customer = AccountDocument.Customer.builder()
                .documentNumber(accountBO.getCustomerDocumentNumber())
                .build();

        return AccountDocument.builder()
                .account(account)
                .customer(customer)
                .build();
    }

    private AccountDocument.AccountTypeEnum toAccountTypeDocument(@NonNull AccountBO.AccountTypeEnum accountType) {
        return AccountDocument.AccountTypeEnum.valueOf(accountType.name());
    }

    public List<AccountBO> toBO(List<AccountDocument> accountsFound) {
        return isEmpty(accountsFound) ? emptyList() : accountsFound.stream().map(this::toBO).collect(toList());
    }

    private AccountBO toBO(@NonNull AccountDocument accountDocument) {
        AccountDocument.Account account = accountDocument.getAccount();
        AccountDocument.Customer customer = accountDocument.getCustomer();

        AccountBO.CreditCard creditCard = AccountBO.CreditCard.builder()
                .limit(account.getCreditCard().getLimit())
                .build();

        AccountBO.Overdraft overdraft = AccountBO.Overdraft.builder()
                .limit(account.getOverdraft().getLimit())
                .build();

        return AccountBO.builder()
                .accountNumber(account.getNumber())
                .bankBranch(account.getBankBranch())
                .customerDocumentNumber(customer.getDocumentNumber())
                .creditCard(creditCard)
                .overdraft(overdraft)
                .accountType(AccountBO.AccountTypeEnum.valueOf(account.getType().name()))
                .build();
    }

}