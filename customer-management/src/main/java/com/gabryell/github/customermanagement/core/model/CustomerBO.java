package com.gabryell.github.customermanagement.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

import static java.util.Objects.isNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerBO {

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotNull(message = "document cannot be null")
    private Document document;

    private int score;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Document {

        @NotNull(message = "Document Number cannot be null")
        private String documentNumber;

        @NotNull(message = "Document Number cannot be null")
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
