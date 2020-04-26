package com.gabryell.github.accountbankingmanagement.core.account.service;

import com.gabryell.github.accountbankingmanagement.core.account.model.AccountBO;
import com.gabryell.github.accountbankingmanagement.data.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

@Slf4j
@Service
public class AccountService {

    private static final int MINIMAL_ACCOUNT_NUMBER_LENGTH = 6;

    @Value("${bank.branch}")
    private int bankBranch;

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createBankingAccountInformation(AccountBO accountBO) {
        createAccountNumber(accountBO);
        createBankBranch(accountBO);
        saveAccount(accountBO);
    }

    public List<AccountBO> listAll() {
        return accountRepository.listAll();
    }

    public void updateAccountWithCredit(BigDecimal creditCardLimit, BigDecimal overdraftLimit, String customerDocumentNumber) {
        accountRepository.updateAccountWithCredit(customerDocumentNumber, creditCardLimit, overdraftLimit);
    }

    private void createAccountNumber(AccountBO accountBO) {
        Random random = new Random();
        int accountNumber = abs(random.nextInt());

        log.info("Creating account number to customer document with number [{}]", accountBO.getCustomerDocumentNumber());
        while (Integer.toString(accountNumber).length() <= MINIMAL_ACCOUNT_NUMBER_LENGTH) {
            accountNumber = random.nextInt();
        }

        String firstSixNumbers = Integer.toString(accountNumber).substring(0, 6);
        log.info("Account number created with number [{}] to customer document number [{}]", firstSixNumbers, accountBO.getCustomerDocumentNumber());

        accountBO.setAccountNumber(parseInt(firstSixNumbers));
    }

    private void createBankBranch(AccountBO accountBO) {
        log.info("Setting bank branch with value from application config to customer document number [{}]", accountBO.getCustomerDocumentNumber());
        accountBO.setBankBranch(bankBranch);
        log.info("Bank branch setted from application properties to customer document number [{}]", accountBO.getCustomerDocumentNumber());
    }

    private void saveAccount(AccountBO accountBO) {
        accountRepository.save(accountBO);
    }

}
