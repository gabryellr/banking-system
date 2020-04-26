package com.gabryell.github.accountbankingmanagement.api.stream.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

import static java.util.Objects.isNull;

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

            public static AccountTypeEnum fromValue(Enum value) {
                if (isNull(value)) return null;

                return Arrays.stream(values())
                        .filter(personTypeEnum -> personTypeEnum.name().equals(value.name()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("[" + AccountTypeEnum.class.getName() + "] not found for value [" + value + "]"));
            }
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
