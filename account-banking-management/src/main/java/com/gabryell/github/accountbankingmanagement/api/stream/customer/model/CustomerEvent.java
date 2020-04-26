package com.gabryell.github.accountbankingmanagement.api.stream.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerEvent {

    private Customer customer;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Customer {

        private String name;
        private Document document;
        private int score;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Document {

        private PersonTypeEnum personType;
        private String documentNumber;

    }

    public enum PersonTypeEnum {

        PF, PJ;

    }

}
