package com.gabryell.github.customermanagement.api.stream.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import static java.util.Objects.isNull;

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

        public static PersonTypeEnum fromValue(Enum value) {
            if (isNull(value)) return null;

            return Arrays.stream(values())
                    .filter(personTypeEnum -> personTypeEnum.name().equals(value.name()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("[" + PersonTypeEnum.class.getName() + "] not found for value [" + value + "]"));
        }

    }

}
