package com.gabryell.github.accountbankingmanagement.core.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AccountBO {

    private String customerDocumentNumber;
    private int accountNumber;
    private AccountTypeEnum accountType;
    private int bankBranch;
    private CreditCard creditCard;
    private Overdraft overdraft;

    public enum AccountTypeEnum {

        INDIVIDUAL_CUSTOMER, JURIDICAL_CUSTOMER;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class CreditCard {

        private BigDecimal limit;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Overdraft {

        private BigDecimal limit;

    }

}