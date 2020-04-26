package com.gabryell.github.customermanagement.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "customers")
public class CustomerDocument {

    @Id
    private String id;

    private Customer customer;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Customer {

        private String name;
        private PersonTypeEnum personType;
        private String documentNumber;
        private int score;

    }

    public enum PersonTypeEnum {

        PF, PJ;

    }

}
