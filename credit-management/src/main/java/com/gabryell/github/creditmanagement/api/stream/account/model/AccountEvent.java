package com.gabryell.github.creditmanagement.api.stream.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountEvent {

    private Account account;
    private Customer customer;

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @Builder
    public static class Account {

        private int accountNumber;
        private AccountTypeEnum accountType;
        private int bankBranch;
        private CreditCard creditCard;
        private Overdraft overdraft;

        public enum AccountTypeEnum {

            INDIVIDUAL_CUSTOMER, JURIDICAL_CUSTOMER;

        }

    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @Builder
    public static class Customer {

        private String customerDocumentNumber;
        private int score;

    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @Builder
    public static class CreditCard {

        private BigDecimal limit;

    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @Builder
    public static class Overdraft {

        private BigDecimal limit;

    }

}
