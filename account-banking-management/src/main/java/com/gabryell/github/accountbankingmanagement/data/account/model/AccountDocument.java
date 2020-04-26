package com.gabryell.github.accountbankingmanagement.data.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "bankAccounts")
public class AccountDocument {

    @Id
    private String id;
    private Account account;
    private Customer customer;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Account {

        private AccountTypeEnum type;
        private int number;
        private int bankBranch;
        private CreditCard creditCard;
        private Overdraft overdraft;

    }

    public enum AccountTypeEnum {

        INDIVIDUAL_CUSTOMER, JURIDICAL_CUSTOMER;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Customer {

        private String documentNumber;

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
