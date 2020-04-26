package com.gabryell.github.customermanagement.api.controller.model;

import com.gabryell.github.customermanagement.core.model.CustomerBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

import static java.util.Objects.isNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOutputDTO {

    private String name;
    private Document document;
    private int score;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Document {

        private String documentNumber;
        private PersonTypeEnum personTypeEnum;

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
