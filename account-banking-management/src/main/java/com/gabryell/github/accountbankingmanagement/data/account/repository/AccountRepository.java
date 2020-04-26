package com.gabryell.github.accountbankingmanagement.data.account.repository;

import com.gabryell.github.accountbankingmanagement.core.account.model.AccountBO;
import com.gabryell.github.accountbankingmanagement.data.account.converter.AccountDocumentConverter;
import com.gabryell.github.accountbankingmanagement.data.account.dao.AccountDAO;
import com.gabryell.github.accountbankingmanagement.data.account.model.AccountDocument;
import com.gabryell.github.accountbankingmanagement.exceptionHandler.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class AccountRepository {

    private AccountDocumentConverter accountDocumentConverter;
    private AccountDAO accountDAO;

    public void save(AccountBO accountBO) {
        log.info("Converting AccountBO [{}] to AccountDocument", accountBO);
        AccountDocument accountDocument = accountDocumentConverter.toDocument(accountBO);

        log.info("AccountBO has been converted to AccountDocument with value [{}] and it going to save", accountDocument);

        accountDAO.save(accountDocument);
        log.info("account saved [{}]", accountDocument);
    }

    public void updateAccountWithCredit(String customerDocumentNumber, BigDecimal creditCardLimit, BigDecimal overdraftLimit) {
        log.info("Searching for AccountDocument with customer document number [{}]", customerDocumentNumber);
        AccountDocument accountDocumentFound = accountDAO.findByCustomerDocumentNumber(customerDocumentNumber)
                .orElseThrow(() -> new BusinessException("Account banking not found with customer document number [" + customerDocumentNumber + "]"));
        log.info("AccountDocument found for the customer document number [{}]", customerDocumentNumber);

        log.info("Updating Account document with credit information");
        AccountDocument.CreditCard.CreditCardBuilder creditCardBuilder = AccountDocument.CreditCard.builder();
        AccountDocument.CreditCard creditCard = creditCardBuilder.limit(creditCardLimit).build();

        AccountDocument.Overdraft.OverdraftBuilder overdraftBuilder = AccountDocument.Overdraft.builder();
        AccountDocument.Overdraft overdraft = overdraftBuilder.limit(overdraftLimit).build();

        AccountDocument.Account account = AccountDocument.Account.builder()
                .bankBranch(accountDocumentFound.getAccount().getBankBranch())
                .creditCard(creditCard)
                .number(accountDocumentFound.getAccount().getNumber())
                .overdraft(overdraft)
                .type(accountDocumentFound.getAccount().getType())
                .build();

        AccountDocument accountDocumentToUpdate = AccountDocument.builder()
                .id(accountDocumentFound.getId())
                .customer(accountDocumentFound.getCustomer())
                .account(account)
                .build();

        log.info("AccountDocument updated with body [{}] and going to save it", accountDocumentToUpdate);
        accountDAO.save(accountDocumentToUpdate);
        log.info("AccountDocument updated saved!");
    }

    public List<AccountBO> listAll() {
        List<AccountDocument> accountsFound = accountDAO.findAll();

        log.info("Converting it to BO if found");
        return accountDocumentConverter.toBO(accountsFound);
    }

}