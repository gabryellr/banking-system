package com.gabryell.github.accountbankingmanagement.api.rest.converter;

import com.gabryell.github.accountbankingmanagement.api.rest.model.AccountOutputDTO;
import com.gabryell.github.accountbankingmanagement.core.account.model.AccountBO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class AccountDtoConverter {

    public List<AccountOutputDTO> toOutputDTO(List<AccountBO> accountsFound) {
        return isEmpty(accountsFound) ? emptyList() : accountsFound.stream().map(this::toOutputDTO).collect(toList());
    }

    private AccountOutputDTO toOutputDTO(@NonNull AccountBO accountBO) {
        AccountOutputDTO.CreditCard creditCard = AccountOutputDTO.CreditCard.builder()
                .limit(accountBO.getCreditCard().getLimit())
                .build();

        AccountOutputDTO.Overdraft overdraft = AccountOutputDTO.Overdraft.builder()
                .limit(accountBO.getOverdraft().getLimit())
                .build();

        return AccountOutputDTO.builder()
                .accountNumber(accountBO.getAccountNumber())
                .bankBranch(accountBO.getBankBranch())
                .creditCard(creditCard)
                .overdraft(overdraft)
                .accountType(AccountOutputDTO.AccountTypeEnum.valueOf(accountBO.getAccountType().name()))
                .customerDocumentNumber(accountBO.getCustomerDocumentNumber())
                .build();
    }

}
